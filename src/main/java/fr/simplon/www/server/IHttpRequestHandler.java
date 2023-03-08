package fr.simplon.www.server;

import java.io.IOException;

/**
 * Interface de traitement de requêtes HTTP.
 */
@FunctionalInterface
public interface IHttpRequestHandler
{
    /**
     * Traite une requête au format HTTP et retourne une réponse au format HTTP.
     *
     * @param pRequest      La requête à traiter.
     * @param pDocumentRoot La racine du répertoire qui contient les documents à servir.
     * @return Une réponse au format HTTP.
     *
     * @throws IOException En cas de problème de traitement de la requête.
     */
    IHttpResponse handle(IHttpRequest pRequest, String pDocumentRoot) throws IOException;
}
