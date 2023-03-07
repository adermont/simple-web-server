package fr.simplon.www;

import fr.simplon.www.requesthandlers.EchoRequestParameters;
import fr.simplon.www.requesthandlers.RedirectToHomepage;
import fr.simplon.www.server.Endpoint;
import fr.simplon.www.server.HttpResponse;
import fr.simplon.www.server.HttpServer;

/**
 * Classe permettant de lancer un serveur de démonstration sur le port 8080.
 * Lancez-la puis <a href="http://localhost:8080">cliquez ici</a> pour vous rendre sur la page d'accueil.
 */
public class DemoWebApp
{
    public static void main(String[] args)
    {
        HttpServer mServer = new HttpServer();
        mServer.addEndpoint(new Endpoint("/", new RedirectToHomepage()));
        mServer.addEndpoint(new Endpoint("/echo", new EchoRequestParameters()));
        mServer.listen(8080);
    }

}
