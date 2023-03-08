package fr.simplon.www.server;

import java.util.Arrays;
import java.util.Collection;

/**
 * Types de contenus définis par le protocole HTTP.
 */
public enum ContentType
{
    //Application
    APPLICATION_EDI_X12("application/EDI-X12"),
    APPLICATION_EDIFACT("application/EDIFACT"),
    APPLICATION_JAVASCRIPT("application/javascript"),
    APPLICATION_OCTET_STREAM("application/octet-stream"),
    APPLICATION_OGG("application/ogg"),
    APPLICATION_PDF("application/pdf"),
    APPLICATION_XHTML_XML("application/xhtml+xml"),
    APPLICATION_X_SHOCKWAVE_FLASH("application/x-shockwave-flash"),
    APPLICATION_JSON("application/json"),
    APPLICATION_LD_JSON("application/ld+json"),
    APPLICATION_XML("application/xml"),
    APPLICATION_ZIP("application/zip"),
    APPLICATION_X_WWW_FORM_URLENCODED("application/x-www-form-urlencoded"),

    //Audio
    AUDIO_MPEG("audio/mpeg"),
    AUDIO_X_MS_WMA("audio/x-ms-wma"),
    AUDIO_VND_RN_REALAUDIO("audio/vnd.rn-realaudio"),
    AUDIO_X_WAV("audio/x-wav"),

    //Image
    IMAGE_GIF("image/gif"),
    IMAGE_JPEG("image/jpeg"),
    IMAGE_PNG("image/png"),
    IMAGE_TIFF("image/tiff"),
    IMAGE_VND_MICROSOFT_ICON("image/vnd.microsoft.icon"),
    IMAGE_X_ICON("image/x-icon"),
    IMAGE_VND_DJVU("image/vnd.djvu"),
    IMAGE_SVG_XML("image/svg+xml"),

    //Multipart
    MULTIPART_MIXED("multipart/mixed"),
    MULTIPART_ALTERNATIVE("multipart/alternative"),
    MULTIPART_RELATED("multipart/related"),
    MULTIPART_FORM_DATA("multipart/form-data"),

    //Text
    TEXT_CSS("text/css"),
    TEXT_CSV("text/csv"),
    TEXT_HTML("text/html"),
    TEXT_PLAIN("text/plain"),
    TEXT_XML("text/xml"),

    //Video
    VIDEO_MPEG("video/mpeg"),
    VIDEO_MP4("video/mp4"),
    VIDEO_QUICKTIME("video/quicktime"),
    VIDEO_X_MS_WMV("video/x-ms-wmv"),
    VIDEO_X_MSVIDEO("video/x-msvideo"),
    VIDEO_X_FLV("video/x-flv"),
    VIDEO_WEBM("video/webm"),

    //VND
    APPLICATION_VND_OASIS_OPENDOCUMENT_TEXT("application/vnd.oasis.opendocument.text"),
    APPLICATION_VND_OASIS_OPENDOCUMENT_SPREADSHEET("application/vnd.oasis.opendocument.spreadsheet"),
    APPLICATION_VND_OASIS_OPENDOCUMENT_PRESENTATION("application/vnd.oasis.opendocument.presentation"),
    APPLICATION_VND_OASIS_OPENDOCUMENT_GRAPHICS("application/vnd.oasis.opendocument.graphics"),
    APPLICATION_VND_MS_EXCEL("application/vnd.ms-excel"),
    APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_SPREADSHEETML_SHEET("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    APPLICATION_VND_MS_POWERPOINT("application/vnd.ms-powerpoint"),
    APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_PRESENTATIONML_PRESENTATION("application/vnd.openxmlformats-officedocument.presentationml.presentation"),
    APPLICATION_MSWORD("application/msword"),
    APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_WORDPROCESSINGML_DOCUMENT("application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    APPLICATION_VND_MOZILLA_XUL_XML("application/vnd.mozilla.xul+xml");

    private final String text;

    ContentType(String pText)
    {
        this.text = pText;
    }

    public String toHttpString()
    {
        return "Content-Type: " + text;
    }

    @Override
    public String toString()
    {
        return toHttpString();
    }

    /**
     * @return Tous les types images reconnus.
     */
    public static Collection<ContentType> images()
    {
        return Arrays.asList(IMAGE_GIF, IMAGE_JPEG, IMAGE_PNG, IMAGE_TIFF, IMAGE_X_ICON, IMAGE_SVG_XML, IMAGE_VND_DJVU, IMAGE_VND_MICROSOFT_ICON);
    }

    /**
     * Retourne les types de contenus qui sont du texte (nécessitant d'ajouter un encodage dans la
     * directive HTTP "ContentType:...; charset=").
     *
     * @return Une collection de ContentTypes de type texte (PLAIN, HTML, CSV, XML, JSON, JAVASCRIPT...).
     */
    public static Collection<ContentType> texts()
    {
        return Arrays.asList(TEXT_PLAIN, TEXT_CSS, TEXT_CSV, TEXT_HTML, TEXT_XML, APPLICATION_JSON, APPLICATION_JAVASCRIPT);
    }
}
