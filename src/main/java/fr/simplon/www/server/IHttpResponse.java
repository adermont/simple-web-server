package fr.simplon.www.server;

/**
 * Une réponse au format HTTP.
 */
public interface IHttpResponse
{
    /**
     * Modifie un paramètre d'entête.
     * @param pName
     * @param pValue
     */
    void setParam(String pName, String pValue);

    /**
     * Modifie le corps de la réponse HTTP.
     * @param pContentType Type de contenu.
     * @param pBody Corps de la réponse.
     */
    void setBody(ContentType pContentType, String pBody);

    /**
     * Modifie le statut de la réponse.
     * @param pStatus le nouveau statut de la réponse.
     */
    void setStatus(HttpResponseStatus pStatus);

    /**
     * @return la réponse au format HTTP.
     */
    String toHttpString();
}
