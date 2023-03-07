package fr.simplon.www.requesthandlers;

import fr.simplon.www.server.*;

/**
 * Redirige systématiquement la requête vers la page d'accueil.
 */
public class RedirectToHomepage extends AbstractHttpRequestHandler
{
    @Override
    public IHttpResponse handle(IHttpRequest pRequest) throws Exception
    {
        return loadStaticFile("/index.html");
    }
}
