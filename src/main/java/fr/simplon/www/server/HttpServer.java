package fr.simplon.www.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Classe principale permettant de lancer un serveur HTTP. Exemple d'utilisation :
 * <pre>
 *     HttpServerserver = new HttpServer();
 *     server.addEndpoint(new Endpoint("/", new RedirectToHomepage()));
 *     server.addEndpoint(new Endpoint("/echo", new EchoRequestParameters()));
 *     server.listen();
 * </pre>
 */
public class HttpServer
{
    /** Port par défaut HTTP utilisé par les navigateurs. */
    public static final int HTTP_DEFAULT_PORT = 80;

    private HttpServerEndpoints mEndpoints;

    /**
     * Constructeur.
     */
    public HttpServer()
    {
        mEndpoints = new HttpServerEndpoints();
    }

    /**
     * Ajoute un nouveau endpoint au serveur.
     *
     * @param pEndpoint Le nouveau endpoint à ajouter.
     */
    public void addEndpoint(Endpoint pEndpoint)
    {
        mEndpoints.addEndpoint(pEndpoint);
    }

    /**
     * Lance l'écoute des requêtes sur le port par défaut (cf {@link #HTTP_DEFAULT_PORT}).
     * <p>
     * Cette méthode est <strong>bloquante</strong>.
     * </p>
     */
    public void listen()
    {
        listen(HTTP_DEFAULT_PORT); // Port par défaut
    }

    /**
     * Lance l'écoute des requêtes sur le port spécifié en paramètre.
     * <p>
     * Cette méthode est <strong>bloquante</strong>.
     * </p>
     */
    public void listen(int port)
    {
        final String newLine = System.lineSeparator();
        try
        {
            ServerSocket socket = new ServerSocket(port);
            while (true)
            {
                Socket connection = socket.accept();
                try
                {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    OutputStream out = new BufferedOutputStream(connection.getOutputStream());
                    PrintStream pout = new PrintStream(out, true, "utf-8");

                    // read first line of request
                    String request = in.readLine();
                    if (request == null)
                    {
                        continue;
                    }

                    // Read request header and request body
                    String content = null;
                    int contentLength = 0;
                    while (true)
                    {
                        // The HTTP request header is composed of "ParamName: ParamValue" lines
                        String headerParam = in.readLine();
                        int iParamSeparator = headerParam.indexOf(": ");
                        if (iParamSeparator > 0)
                        {
                            String headerParamName = headerParam.substring(0, iParamSeparator);
                            String headerParamValue = headerParam.substring(iParamSeparator + ": ".length());
                            if (headerParamName.equals("Content-Length"))
                            {
                                contentLength = Integer.parseInt(headerParamValue);
                            }
                        }
                        // The last line is an empty line and next element is an optional body (if Content-Length > 0)
                        if (headerParam == null || headerParam.length() == 0)
                        {
                            if (contentLength > 0)
                            {
                                // Read the request body
                                char[] read = new char[contentLength];
                                in.read(read, 0, contentLength);
                                content = new String(read);
                            }
                            break;
                        }
                    }
                    if (!(request.endsWith(" HTTP/1.0") || request.endsWith(" HTTP/1.1")))
                    {
                        pout.print("HTTP/1.0 400 Bad Request" + newLine + newLine);
                    }
                    else
                    {
                        try
                        {
                            IHttpResponse response = process(request, content);
                            pout.print(response.toHttpString());
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            pout.print("HTTP/1.0 500 Server error" + newLine + newLine);
                            e.printStackTrace(pout);
                        }
                    }

                    pout.close();
                }
                catch (Throwable tri)
                {
                    System.err.println("Error handling request: " + tri);
                }
            }
        }
        catch (Throwable tr)
        {
            System.err.println("Could not start server: " + tr);
        }
    }

    /**
     * Analyse et traitement de la requête brute.
     *
     * @param pRequest Chaîne brute de la requête au format "HTTP1.0 GET &lt;url&gt;".
     * @param pBody Corps de la requête.
     * @return
     * @throws Exception
     */
    private IHttpResponse process(String pRequest, String pBody) throws Exception
    {
        Scanner scanner = new Scanner(pRequest);
        String sMethod = scanner.next();
        HttpMethod method = HttpMethod.valueOf(sMethod);
        String fullUrl = scanner.next();
        String url = fullUrl;
        int iParamSeparator = fullUrl.indexOf('?');
        String paramString = "";
        if (iParamSeparator >= 0)
        {
            url = url.substring(0, iParamSeparator);
            paramString = fullUrl.substring(iParamSeparator + 1);
        }
        HttpRequest httpRequest = new HttpRequest(method, url, paramString);

        if (pBody != null && !pBody.isEmpty())
        {
            scanner = new Scanner(pBody);
            scanner.useDelimiter("&");
            while (scanner.hasNext())
            {
                String next = scanner.next();
                httpRequest.addParameter(next);
            }
        }

        IHttpResponse result = process(httpRequest);
        return result;
    }

    /**
     * Traitement de la requête.
     *
     * @param pIHttpRequest La requête structurée en format objet java.
     * @return Une réponse HTTP.
     * @throws Exception
     */
    private IHttpResponse process(IHttpRequest pIHttpRequest) throws Exception
    {
        Endpoint endpoint = mEndpoints.match(pIHttpRequest.getUrl());
        return endpoint.process(pIHttpRequest);
    }

}
