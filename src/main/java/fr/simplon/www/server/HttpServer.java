package fr.simplon.www.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Scanner;

/**
 * Classe principale permettant de lancer un serveur HTTP. Exemple d'utilisation :
 * <pre>
 *     HttpServer server = new HttpServer();
 *     server.addEndpoint(new Endpoint("/", new RedirectToHomepage()));
 *     server.addEndpoint(new Endpoint("/echo", new EchoRequestParameters()));
 *     server.listen();
 * </pre>
 */
public class HttpServer
{
    public static final System.Logger logger = System.getLogger(HttpServer.class.getName());

    /** Port par défaut HTTP utilisé par les navigateurs. */
    public static final int HTTP_DEFAULT_PORT = 80;

    private final HttpServerEndpoints mEndpoints;
    private final String              mDocumentRoot;
    private       boolean             mIsRunning;

    /**
     * Constructeur.
     */
    public HttpServer()
    {
        this(".");
    }

    /**
     * Constructeur.
     *
     * @param pDocumentRoot Racine des fichiers servis par le serveur.
     */
    public HttpServer(String pDocumentRoot)
    {
        super();
        mIsRunning = false;
        mEndpoints = new HttpServerEndpoints();
        mDocumentRoot = pDocumentRoot;
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
     * Arrêt du serveur.
     */
    public void stop()
    {
        mIsRunning = false;
    }

    /**
     * Lance l'écoute des requêtes sur le port spécifié en paramètre.
     * <p>
     * Cette méthode est <strong>bloquante</strong>.
     * </p>
     */
    public void listen(int port)
    {
        try (ServerSocket socket = new ServerSocket(port))
        {
            mIsRunning = true;
            while (mIsRunning)
            {
                acceptConnection(socket.accept());
            }
        }
        catch (Exception tr)
        {
            logger.log(System.Logger.Level.ERROR, "Impossible de démarrer le serveur", tr);
        } finally
        {
            mIsRunning = false;
        }
    }

    /**
     * Accepte une nouvelle connexion.
     *
     * @param connection La connexion d'un nouveau client.
     */
    private void acceptConnection(Socket connection)
    {
        PrintStream out = null;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream())))
        {
            out = new PrintStream(new BufferedOutputStream(connection.getOutputStream()), true, StandardCharsets.UTF_8);

            // read first line of request
            String request = in.readLine();
            if (request != null)
            {
                String protocolVersion = request.substring(request.lastIndexOf(' ') + 1);
                Optional<HttpVersion> supportedVersion = HttpVersion.from(protocolVersion);

                if (supportedVersion.isEmpty())
                {
                    HttpResponse error = HttpResponse.error(HttpResponseStatus.HTTP_400_BAD_REQUEST, request);
                    out.println(error.toHttpString());
                }
                else
                {
                    processValidRequest(in, out, request);
                }
            }
        }
        catch (Exception tri)
        {
            logger.log(System.Logger.Level.ERROR, "Erreur fatale", tri);
        } finally
        {
            if (out != null)
            {
                out.close();
            }
        }
    }

    /**
     * Traitement d'une requête valide.
     *
     * @param pIn         Flux d'entrée de lecture de la requête.
     * @param pOut        Flux de sortie pour la réponse.
     * @param pRawRequest La requête brute.
     * @throws IOException En cas de problème de traitement de la requête.
     */
    private void processValidRequest(BufferedReader pIn, PrintStream pOut, String pRawRequest)
            throws IOException
    {
        String newLine = System.lineSeparator();

        // Read pRawRequest header and pRawRequest body
        Optional<String> content = getRequestBody(pIn);
        try
        {
            IHttpResponse response = process(pRawRequest, content.orElse(""));
            pOut.print(response.toHttpString());
        }
        catch (Exception e)
        {
            logger.log(System.Logger.Level.ERROR, "Erreur de traitement de la requête '{0}'", pRawRequest, e);
            pOut.print("HTTP/1.0 500 Server error" + newLine + newLine);
            e.printStackTrace(pOut);
        }
    }

    /**
     * Extrait le corps de la requête HTTP, c'est à dire le contenu après le double-retour à la
     * ligne.
     *
     * @param in Le flux entrant de la requête.
     * @return Le contenu optionnel du corps de la requête.
     *
     * @throws IOException En cas de problème de traitement de la requête.
     */
    private static Optional<String> getRequestBody(BufferedReader in) throws IOException
    {
        String content = null;
        int contentLength = 0;

        boolean continueReading = true;
        while (continueReading)
        {
            // The HTTP request header is composed of "ParamName: ParamValue" lines
            String headerParam = in.readLine();
            // The last line is an empty line and next element is an optional body (if Content-Length > 0)
            if (headerParam == null || headerParam.length() == 0)
            {
                if (contentLength > 0)
                {
                    // Read the request body
                    char[] read = new char[contentLength];
                    int count = in.read(read, 0, contentLength);
                    if (count < contentLength)
                    {
                        logger.log(System.Logger.Level.TRACE, "EOF prématuré de la requête");
                    }
                    content = new String(read, 0, count);
                }
                continueReading = false;
            }
            else
            {
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
            }
        }
        return Optional.ofNullable(content);
    }

    /**
     * Analyse et traitement de la requête brute.
     *
     * @param pRequest Chaîne brute de la requête au format "HTTP1.0 GET &lt;url&gt;".
     * @param pBody    Corps de la requête.
     * @return La réponse au format HTTP.
     *
     * @throws IOException En cas de problème de traitement de la requête.
     */
    private IHttpResponse process(String pRequest, String pBody) throws IOException
    {
        try (Scanner scanner = new Scanner(pRequest))
        {
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

            if (pBody != null)
            {
                try (Scanner bodyScanner = new Scanner(pBody))
                {
                    bodyScanner.useDelimiter("&");
                    while (bodyScanner.hasNext())
                    {
                        String next = bodyScanner.next();
                        httpRequest.addParameter(next);
                    }
                }
            }
            return process(httpRequest);
        }
    }

    /**
     * Traitement de la requête.
     *
     * @param pIHttpRequest La requête structurée en format objet java.
     * @return Une réponse HTTP.
     *
     * @throws IOException En cased de problème de traitement de la requête.
     */
    private IHttpResponse process(IHttpRequest pIHttpRequest) throws IOException
    {
        Endpoint endpoint = mEndpoints.match(pIHttpRequest.getUrl());
        endpoint.setDocumentRoot(mDocumentRoot);
        return endpoint.process(pIHttpRequest);
    }

}
