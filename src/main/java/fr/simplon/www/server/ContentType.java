package fr.simplon.www.server;

/**
 * Types de contenus définis par le protocole HTTP.
 */
public enum ContentType
{
    //Application
    application_EDI_X12("application/EDI-X12"),
    application_EDIFACT("application/EDIFACT"),
    application_javascript("application/javascript"),
    application_octet_stream("application/octet-stream"),
    application_ogg("application/ogg"),
    application_pdf("application/pdf"),
    application_xhtml_xml("application/xhtml+xml"),
    application_x_shockwave_flash("application/x-shockwave-flash"),
    application_json("application/json"),
    application_ld_json("application/ld+json"),
    application_xml("application/xml"),
    application_zip("application/zip"),
    application_x_www_form_urlencoded("application/x-www-form-urlencoded"),

    //Audio
    audio_mpeg("audio/mpeg"),
    audio_x_ms_wma("audio/x-ms-wma"),
    audio_vnd_rn_realaudio("audio/vnd.rn-realaudio"),
    audio_x_wav("audio/x-wav"),

    //Image
    image_gif("image/gif"),
    image_jpeg("image/jpeg"),
    image_png("image/png"),
    image_tiff("image/tiff"),
    image_vnd_microsoft_icon("image/vnd.microsoft.icon"),
    image_x_icon("image/x-icon"),
    image_vnd_djvu("image/vnd.djvu"),
    image_svg_xml("image/svg+xml"),

    //Multipart
    multipart_mixed("multipart/mixed"),
    multipart_alternative("multipart/alternative"),
    multipart_related("multipart/related"),
    multipart_form_data("multipart/form-data"),

    //Text
    text_css("text/css"),
    text_csv("text/csv"),
    text_html("text/html"),
    text_plain("text/plain"),
    text_xml("text/xml"),

    //Video
    video_mpeg("video/mpeg"),
    video_mp4("video/mp4"),
    video_quicktime("video/quicktime"),
    video_x_ms_wmv("video/x-ms-wmv"),
    video_x_msvideo("video/x-msvideo"),
    video_x_flv("video/x-flv"),
    video_webm("video/webm"),

    //VND
    application_vnd_oasis_opendocument_text("application/vnd.oasis.opendocument.text"),
    application_vnd_oasis_opendocument_spreadsheet("application/vnd.oasis.opendocument.spreadsheet"),
    application_vnd_oasis_opendocument_presentation("application/vnd.oasis.opendocument.presentation"),
    application_vnd_oasis_opendocument_graphics("application/vnd.oasis.opendocument.graphics"),
    application_vnd_ms_excel("application/vnd.ms-excel"),
    application_vnd_openxmlformats_officedocument_spreadsheetml_sheet("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    application_vnd_ms_powerpoint("application/vnd.ms-powerpoint"),
    application_vnd_openxmlformats_officedocument_presentationml_presentation("application/vnd.openxmlformats-officedocument.presentationml.presentation"),
    application_msword("application/msword"),
    application_vnd_openxmlformats_officedocument_wordprocessingml_document("application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    application_vnd_mozilla_xul_xml("application/vnd.mozilla.xul+xml");

    private String text;

    private ContentType(String pText)
    {
        this.text = pText;
    }

    @Override
    public String toString()
    {
        return "Content-Type: " + text;
    }
}
