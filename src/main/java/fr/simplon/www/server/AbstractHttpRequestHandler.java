package fr.simplon.www.server;

import java.io.InputStream;

/**
 * Classe de base pour l'implémentation du traitement d'une requête.
 */
public abstract class AbstractHttpRequestHandler implements IHttpRequestHandler
{
    /**
     * {@inheritDoc}
     */
    @Override
    public IHttpResponse handle(IHttpRequest pRequest) throws Exception
    {
        return loadStaticFile(pRequest.getUrl());
    }

    /**
     * Charge un fichier HTML depuis le disque. Le fichier doit se trouver à la racine d'un répertoire
     * source.
     *
     * @param resourceUrl L'URL du fichier à charger.
     * @return La réponse HTTP (ContentType=text/html) dont le corps est le contenu du fichier HTML.
     * @throws Exception En cas de problème de lecture du fichier.
     */
    protected IHttpResponse loadStaticFile(String resourceUrl) throws Exception
    {
        try (InputStream stream = getClass().getResourceAsStream(resourceUrl))
        {
            if (stream != null)
            {
                String content = new String(stream.readAllBytes());
                return HttpResponse.textHtml(content);
            }
            return HttpResponse.error(HttpResponseStatus.HTTP_404_Not_Found, resourceUrl);
        }
    }
}
