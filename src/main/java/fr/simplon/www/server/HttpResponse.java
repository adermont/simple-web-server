package fr.simplon.www.server;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Une réponse HTTP avec des paramètres et un body.
 */
public class HttpResponse implements IHttpResponse
{
    /**
     * Crée une nouvelle réponse au format text/plain.
     *
     * @param pContent Contenu de la réponse en texte brut.
     * @return Un objet HttpResponse.
     */
    public static HttpResponse textPlain(String pContent)
    {
        return new HttpResponse(ContentType.text_plain, pContent);
    }

    /**
     * Crée une nouvelle réponse au format text/plain contenant le code d'erreur mentionné en paramètre.
     *
     * @param errCode Le code d'erreur de la réponse.
     * @param url L'URL de la requête qui a échoué.
     * @return Un objet HttpResponse.
     */
    public static HttpResponse error(HttpResponseStatus errCode, String url)
    {
        HttpResponse response = new HttpResponse(ContentType.text_plain, url);
        response.setStatus(errCode);
        return response;
    }

    /**
     * Crée une nouvelle réponse au format text/html.
     *
     * @param pContent Contenu de la réponse.
     * @return La réponse au format HTML.
     */
    public static IHttpResponse textHtml(String pContent)
    {
        return new HttpResponse(ContentType.text_html, pContent);
    }

    // --------------------------------------------------------------

    private Map<String, String> mParams;
    private ContentType         mContentType;
    private StringBuilder       mBody;
    private HttpResponseStatus  mStatus;

    /**
     * Constructeur.
     */
    public HttpResponse()
    {
        this(ContentType.text_plain, "");
    }

    /**
     * Constructeur.
     *
     * @param pContentType
     * @param pBody
     */
    public HttpResponse(ContentType pContentType, String pBody)
    {
        mParams = new HashMap<>();
        mBody = new StringBuilder();
        mBody.append(pBody);
        mContentType = pContentType;
        mStatus = HttpResponseStatus.HTTP_200_OK;
    }

    /**
     * Modifie un paramètre.
     * @param pName Nom du paramètre.
     * @param pValue Valeur du paramètre.
     */
    @Override
    public void setParam(String pName, String pValue)
    {
        mParams.put(pName, pValue);
    }

    /**
     * Modifie le corps de la réponse.
     * @param pContentType Le type de contenu du body.
     * @param pBody Le body de la réponse.
     */
    @Override
    public void setBody(ContentType pContentType, String pBody)
    {
        mContentType = pContentType;
        mBody.delete(0, mBody.length());
    }

    /**
     * Modifie le statut de la réponse.
     * @param pStatus le nouveau statut de la réponse.
     */
    public void setStatus(HttpResponseStatus pStatus)
    {
        mStatus = pStatus;
    }

    /**
     * Ajoute du contenu dans le corps de la réponse, à la suite.
     * @param pString Nouveau contenu à ajouter.
     */
    public void append(String pString)
    {
        mBody.append(pString);
    }

    public String toHttpString()
    {
        String newLine = System.lineSeparator();
        StringBuilder sb = new StringBuilder(String.format("HTTP/1.0 %d %s", mStatus.getCode(), mStatus.getMessage())).append(newLine);
        //sb.append(newLine);
        sb.append(mContentType.toString()).append("; charset=UTF-8").append(newLine);
        sb.append("Date: " + new Date().toString() + newLine);

        String body = mBody.toString();
        sb.append("Content-length: ");
        try
        {
            byte[] bytes = body.getBytes("utf-8");
            sb.append(bytes.length).append(newLine).append(newLine).append(body);
        }
        catch (UnsupportedEncodingException pE)
        {
            byte[] bytes = body.getBytes();
            sb.append(bytes.length).append(newLine).append(newLine).append(body);
        }
        return sb.toString();
    }
}
