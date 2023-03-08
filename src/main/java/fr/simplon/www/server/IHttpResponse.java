package fr.simplon.www.server;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;

/**
 * Une réponse au format HTTP.
 */
public interface IHttpResponse
{
    /**
     * Modifie un paramètre d'entête.
     *
     * @param pName  Nom du paramètre.
     * @param pValue Valeur du paramètre.
     */
    void setParam(String pName, String pValue);

    /**
     * @return Les paramètres HTTP de la réponse.
     */
    Map<String, String> getParams();

    /**
     * Modifie le corps de la réponse HTTP.
     *
     * @param pContentType Type de contenu.
     * @param pBody        Corps de la réponse.
     */
    void setBody(ContentType pContentType, byte[] pBody);

    /**
     * Modifie le statut de la réponse.
     *
     * @param pStatus le nouveau statut de la réponse.
     */
    void setStatus(HttpResponseStatus pStatus);

    /**
     * @return Le status de la réponse.
     */
    HttpResponseStatus getStatus();

    /**
     * Ecrit la réponse au format HTTP dans un flux sortant.
     *
     * @param out Le flux de sortie.
     */
    void writeTo(PrintStream out) throws IOException;
}
