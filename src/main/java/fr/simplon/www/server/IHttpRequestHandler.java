package fr.simplon.www.server;

/**
 * Interface de traitement de requêtes HTTP.
 */
@FunctionalInterface
public interface IHttpRequestHandler
{
    /**
     * Traite une requête au format HTTP et retourne une réponse au format HTTP.
     *
     * @param pRequest
     * @return
     * @throws Exception
     */
    IHttpResponse handle(IHttpRequest pRequest) throws Exception;
}
