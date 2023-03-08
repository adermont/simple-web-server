package fr.simplon.www.server;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    public static IHttpResponse fromFileExtension(String pFileExtension, byte[] content)
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
        else if ("jpg".equalsIgnoreCase(pFileExtension) || "jpeg".equalsIgnoreCase(pFileExtension))
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
        else if ("json".equalsIgnoreCase(pFileExtension))
        {
            return json(content);
        }
        else if ("xml".equalsIgnoreCase(pFileExtension))
        {
            return textXml(content);
        }
        return textPlain(content);
    }

    /**
     * Crée une nouvelle réponse au format text/plain.
     *
     * @param pContent Contenu de la réponse en texte brut.
     * @return Un objet HttpResponse.
     */
    public static IHttpResponse textPlain(byte[] pContent)
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
    public static IHttpResponse error(HttpResponseStatus errCode, byte[] url)
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
    public static IHttpResponse textHtml(byte[] pContent)
    {
        return new HttpResponse(ContentType.TEXT_HTML, pContent);
    }

    /**
     * Crée une nouvelle réponse au format text/css.
     *
     * @param pContent Contenu de la réponse.
     * @return La réponse au format CSS.
     */
    public static IHttpResponse textCss(byte[] pContent)
    {
        return new HttpResponse(ContentType.TEXT_CSS, pContent);
    }

    /**
     * Crée une nouvelle réponse au format text/xml.
     *
     * @param pContent Contenu de la réponse.
     * @return La réponse au format XML.
     */
    public static IHttpResponse textXml(byte[] pContent)
    {
        return new HttpResponse(ContentType.TEXT_XML, pContent);
    }

    /**
     * Crée une nouvelle réponse au format text/csv.
     *
     * @param pContent Contenu de la réponse.
     * @return La réponse au format CSV.
     */
    public static IHttpResponse textCsv(byte[] pContent)
    {
        return new HttpResponse(ContentType.TEXT_CSV, pContent);
    }

    /**
     * Crée une nouvelle réponse au format image/png.
     *
     * @param pContent Contenu de la réponse.
     * @return La réponse au format image/png.
     */
    public static IHttpResponse imagePng(byte[] pContent)
    {
        return new HttpResponse(ContentType.IMAGE_PNG, pContent);
    }

    /**
     * Crée une nouvelle réponse au format image/jpeg.
     *
     * @param pContent Contenu de la réponse.
     * @return La réponse au format image/jpeg.
     */
    public static IHttpResponse imageJpeg(byte[] pContent)
    {
        return new HttpResponse(ContentType.IMAGE_JPEG, pContent);
    }

    /**
     * Crée une nouvelle réponse au format image/tiff.
     *
     * @param pContent Contenu de la réponse.
     * @return La réponse au format image/tiff.
     */
    public static IHttpResponse imageTiff(byte[] pContent)
    {
        return new HttpResponse(ContentType.IMAGE_TIFF, pContent);
    }

    /**
     * Crée une nouvelle réponse au format image/gif.
     *
     * @param pContent Contenu de la réponse.
     * @return La réponse au format image/gif.
     */
    public static IHttpResponse imageGif(byte[] pContent)
    {
        return new HttpResponse(ContentType.IMAGE_GIF, pContent);
    }

    /**
     * Crée une nouvelle réponse au format image/x-icon.
     *
     * @param pContent Contenu de la réponse.
     * @return La réponse au format image/x-icon.
     */
    public static IHttpResponse imageXIcon(byte[] pContent)
    {
        return new HttpResponse(ContentType.IMAGE_X_ICON, pContent);
    }

    /**
     * Crée une nouvelle réponse au format image/svg.
     *
     * @param pContent Contenu de la réponse.
     * @return La réponse au format image/svg.
     */
    public static IHttpResponse imageSvg(byte[] pContent)
    {
        return new HttpResponse(ContentType.IMAGE_SVG_XML, pContent);
    }

    /**
     * Crée une nouvelle réponse au format application/json.
     *
     * @param pContent Contenu de la réponse.
     * @return La réponse au format application/json.
     */
    public static IHttpResponse json(byte[] pContent)
    {
        return new HttpResponse(ContentType.APPLICATION_JSON, pContent);
    }

    // --------------------------------------------------------------

    private final HttpVersion         mHttpVersion;
    private       HttpResponseStatus  mStatus;
    private final Charset             mCharset;
    private       ContentType         mContentType;
    private final Map<String, String> mParams;
    private       byte[]              mBody;

    /**
     * Constructeur.
     */
    public HttpResponse()
    {
        this(ContentType.TEXT_PLAIN, new byte[0]);
    }

    /**
     * Constructeur.
     *
     * @param pContentType Type de contenu.
     * @param pBody        Corps de la réponse.
     */
    public HttpResponse(ContentType pContentType, byte[] pBody)
    {
        this(pContentType, pBody, StandardCharsets.UTF_8);
    }

    /**
     * Constructeur où on peut modifier le charset.
     *
     * @param pContentType Type de contenu.
     * @param pBody        Corps de la réponse.
     * @param pCharset     Jeu de caractères.
     */
    public HttpResponse(ContentType pContentType, byte[] pBody, Charset pCharset)
    {
        this(pContentType, pBody, pCharset, HttpVersion.HTTP_1_1);
    }

    /**
     * Constructeur où on peut modifier le charset.
     *
     * @param pContentType Type de contenu.
     * @param pBody        Corps de la réponse.
     * @param pCharset     Jeu de caractères.
     * @param pVersion     Version du protocole HTTP.
     */
    public HttpResponse(
            ContentType pContentType, byte[] pBody, Charset pCharset, HttpVersion pVersion)
    {
        mParams = new HashMap<>();
        mStatus = HttpResponseStatus.HTTP_200_OK;
        mBody = pBody;
        mContentType = Optional.ofNullable(pContentType).orElse(ContentType.TEXT_PLAIN);
        mCharset = Optional.ofNullable(pCharset).orElse(StandardCharsets.UTF_8);
        mHttpVersion = Optional.ofNullable(pVersion).orElse(HttpVersion.HTTP_1_1);
    }

    /**
     * Efface les paramètres, le statut le contenu du body.
     */
    public void clear()
    {
        mParams.clear();
        mBody = null;
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
        if (pName != null && !pName.contains(":") && !pName.equals("ContentType"))
        {
            mParams.put(pName, pValue);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> getParams()
    {
        return mParams;
    }

    /**
     * Modifie le corps de la réponse.
     *
     * @param pContentType Le type de contenu du body.
     * @param pBody        Le body de la réponse.
     */
    @Override
    public void setBody(ContentType pContentType, byte[] pBody)
    {
        mContentType = pContentType;
        mBody = pBody;
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

    @Override
    public HttpResponseStatus getStatus()
    {
        return mStatus;
    }

    /**
     * Ecrit la réponse dans un flux de sortie au format HTTP, y compris les deux lignes vide de
     * terminaison.
     */
    public void writeTo(PrintStream out) throws IOException
    {
        out.printf("%s %d %s%n", mHttpVersion.toHttpString(), mStatus.getCode(), mStatus.getMessage());

        // ContentType
        out.print(mContentType.toHttpString());

        // Si c'est du texte on ajoute une directive pour le charset
        if (ContentType.texts().contains(mContentType))
        {
            out.printf("; charset=%s", mCharset.name());
        }
        out.println();

        // Date
        out.printf("Date: %s%n", new Date());

        // Content-Length
        out.printf("Content-length: %d", mBody.length);

        // Other parameters
        for (Map.Entry<String, String> params : mParams.entrySet())
        {
            out.printf("%s=%s%n", params.getKey(), params.getValue());
        }

        // Body
        out.printf("%n%n");
        if (ContentType.texts().contains(mContentType))
        {
            String body = new String(mBody);
            out.print(body);
        }
        else
        {
            out.write(mBody);
        }
    }
}
