package fr.simplon.www;

import fr.simplon.www.requesthandlers.EchoRequestParameters;
import fr.simplon.www.requesthandlers.ReturnResource;
import fr.simplon.www.server.Endpoint;
import fr.simplon.www.server.HttpServer;
import fr.simplon.www.server.IHttpRequestHandler;

/**
 * Classe permettant de lancer un serveur de démonstration sur le port 8080.
 * Lancez-la puis <a href="http://localhost:8080">cliquez ici</a> pour vous rendre sur la page d'accueil.
 */
public class DemoWebApp
{
    public static void main(String[] args)
    {
        HttpServer mServer = new HttpServer("src/test/html");

        IHttpRequestHandler defaultHandler = new ReturnResource();

        mServer.addEndpoint(new Endpoint("/", new ReturnResource("index.html")));
        mServer.addEndpoint(new Endpoint("/index.html", defaultHandler));
        mServer.addEndpoint(new Endpoint("/styles.css", defaultHandler));
        mServer.addEndpoint(new Endpoint("/logo.png", defaultHandler));
        mServer.addEndpoint(new Endpoint("/favicon.ico", defaultHandler));
        mServer.addEndpoint(new Endpoint("/echo", new EchoRequestParameters()));
        mServer.listen(8080);
    }

}
