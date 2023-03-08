package fr.simplon.www.server;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Une réponse HTTP avec des paramètres et un body.
 */
public class HttpResponse implements IHttpResponse
{
    /**
     * Crée une réponse à partir de l'extension du fichier.
     *
     * @param pFileExtension L'extension de fichier, sans le '.'.
     * @param content        Contenu du fichier.
     * @return Une réponse HTTP dont le content-type correspond à l'extension de fichier fournie.
     */
    public static IHttpResponse fromFileExtension(String pFileExtension, String content)
    {
        if (("htm").equalsIgnoreCase(pFileExtension) || "html".equalsIgnoreCase(pFileExtension))
        {
            return textHtml(content);
        }
        else if ("css".equalsIgnoreCase(pFileExtension) || "less".equalsIgnoreCase(pFileExtension) || ".sass".equalsIgnoreCase(pFileExtension) || ".lcss".equalsIgnoreCase(pFileExtension))
        {
            return textCss(content);
        }
        else if ("png".equalsIgnoreCase(pFileExtension))
        {
            return imagePng(content);
        }
        else if ("gif".equalsIgnoreCase(pFileExtension))
        {
            return imageGif(content);
        }
        else if ("tiff".equalsIgnoreCase(pFileExtension))
        {
            return imageTiff(content);
        }
        else if ("jpeg".equalsIgnoreCase(pFileExtension))
        {
            return imageJpeg(content);
        }
        else if ("ico".equalsIgnoreCase(pFileExtension))
        {
            return imageXIcon(content);
        }
        else if ("svg".equalsIgnoreCase(pFileExtension))
        {
            return imageSvg(content);
        }
        return textPlain(content);
    }

    /**
     * Crée une nouvelle réponse au format text/plain.
     *
     * @param pContent Contenu de la réponse en texte brut.
     * @return Un objet HttpResponse.
     */
    public static HttpResponse textPlain(String pContent)
    {
        return new HttpResponse(ContentType.TEXT_PLAIN, pContent);
    }

    /**
     * Crée une nouvelle réponse au format text/plain contenant le code d'erreur mentionné en
     * paramètre.
     *
     * @param errCode Le code d'erreur de la réponse.
     * @param url     L'URL de la requête qui a échoué.
     * @return Un objet HttpResponse.
     */
    public static HttpResponse error(HttpResponseStatus errCode, String url)
    {
        HttpResponse response = new HttpResponse(ContentType.TEXT_PLAIN, url);
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
        return new HttpResponse(ContentType.TEXT_HTML, pContent);
    }

    /**
     * Crée une nouvelle réponse au format text/css.
     *
     * @param pContent Contenu de la réponse.
     * @return La réponse au format CSS.
     */
    public static IHttpResponse textCss(String pContent)
    {
        return new HttpResponse(ContentType.TEXT_CSS, pContent);
    }

    /**
     * Crée une nouvelle réponse au format image/png.
     *
     * @param pContent Contenu de la réponse.
     * @return La réponse au format image/png.
     */
    public static IHttpResponse imagePng(String pContent)
    {
        return new HttpResponse(ContentType.IMAGE_PNG, pContent);
    }

    /**
     * Crée une nouvelle réponse au format image/jpeg.
     *
     * @param pContent Contenu de la réponse.
     * @return La réponse au format image/jpeg.
     */
    public static IHttpResponse imageJpeg(String pContent)
    {
        return new HttpResponse(ContentType.IMAGE_JPEG, pContent);
    }

    /**
     * Crée une nouvelle réponse au format image/tiff.
     *
     * @param pContent Contenu de la réponse.
     * @return La réponse au format image/tiff.
     */
    public static IHttpResponse imageTiff(String pContent)
    {
        return new HttpResponse(ContentType.IMAGE_TIFF, pContent);
    }

    /**
     * Crée une nouvelle réponse au format image/gif.
     *
     * @param pContent Contenu de la réponse.
     * @return La réponse au format image/gif.
     */
    public static IHttpResponse imageGif(String pContent)
    {
        return new HttpResponse(ContentType.IMAGE_GIF, pContent);
    }

    /**
     * Crée une nouvelle réponse au format image/x-icon.
     *
     * @param pContent Contenu de la réponse.
     * @return La réponse au format image/x-icon.
     */
    public static IHttpResponse imageXIcon(String pContent)
    {
        return new HttpResponse(ContentType.IMAGE_X_ICON, pContent);
    }

    /**
     * Crée une nouvelle réponse au format image/svg.
     *
     * @param pContent Contenu de la réponse.
     * @return La réponse au format image/svg.
     */
    public static IHttpResponse imageSvg(String pContent)
    {
        return new HttpResponse(ContentType.IMAGE_SVG_XML, pContent);
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
        this(ContentType.TEXT_PLAIN, "");
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
     *
     * @param pName  Nom du paramètre.
     * @param pValue Valeur du paramètre.
     */
    @Override
    public void setParam(String pName, String pValue)
    {
        mParams.put(pName, pValue);
    }

    /**
     * Modifie le corps de la réponse.
     *
     * @param pContentType Le type de contenu du body.
     * @param pBody        Le body de la réponse.
     */
    @Override
    public void setBody(ContentType pContentType, String pBody)
    {
        mContentType = pContentType;
        mBody.delete(0, mBody.length());
    }

    /**
     * Modifie le statut de la réponse.
     *
     * @param pStatus le nouveau statut de la réponse.
     */
    public void setStatus(HttpResponseStatus pStatus)
    {
        mStatus = pStatus;
    }

    /**
     * Ajoute du contenu dans le corps de la réponse, à la suite.
     *
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

        sb.append(mContentType.toString()).append("; charset=UTF-8").append(newLine);
        sb.append("Date: " + new Date().toString() + newLine);

        String body = mBody.toString();
        sb.append("Content-length: ");

        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        sb.append(bytes.length).append(newLine).append(newLine).append(body);
        return sb.toString();
    }
}
