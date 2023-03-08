package fr.simplon.www.server;

import java.util.Arrays;

/**
 * Les différents types de status définis par le protocole HTTP.
 */
public enum HttpResponseStatus
{
    HTTP_100_CONTINUE(100, "Continue"),//	[RFC9110, Section 15.2.1]
    HTTP_101_SWITCHING_PROTOCOLS(101, "Switching Protocols"),//[RFC9110, Section 15.2.2]
    HTTP_102_PROCESSING(102, "Processing"),//[RFC2518]
    HTTP_103_EARLY_HINTS(103, "Early Hints"),//[RFC8297]

    HTTP_200_OK(200, "OK"),    //[RFC9110, Section 15.3.1]
    HTTP_201_CREATED(201, "Created"),//[RFC9110, Section 15.3.2]
    HTTP_202_ACCEPTED(202, "Accepted"),//[RFC9110, Section 15.3.3]
    HTTP_203_NON_AUTHORITATIVE_INFORMATION(203, "Non Authoritative Information"),//[RFC9110, Section 15.3.4]
    HTTP_204_NO_CONTENT(204, "No Content"),//[RFC9110, Section 15.3.5]
    HTTP_205_RESET_CONTENT(205, "Reset Content"),//[RFC9110, Section 15.3.6]
    HTTP_206_PARTIAL_CONTENT(206, "Partial Content"),//	[RFC9110, Section 15.3.7]
    HTTP_207_MULTI_STATUS(207, "Multi Status"),//[RFC4918]
    HTTP_208_ALREADY_REPORTED(208, "Already Reported"),//[RFC5842]

    HTTP_226_IM_USED(226, "IM Used"),//[RFC3229]

    HTTP_300_MULTIPLE_CHOICES(300, "Multiple Choices"),//[RFC9110, Section 15.4.1]
    HTTP_301_MOVED_PERMANENTLY(301, "Moved Permanently"),//[RFC9110, Section 15.4.2]
    HTTP_302_FOUND(302, "Found"),//[RFC9110, Section 15.4.3]
    HTTP_303_SEE_OTHER(303, "See Other"),//[RFC9110, Section 15.4.4]
    HTTP_304_NOT_MODIFIED(304, "Not Modified"),//[RFC9110, Section 15.4.5]
    HTTP_305_USE_PROXY(305, "Use Proxy"),//[RFC9110, Section 15.4.6]
    HTTP_307_TEMPORARY_REDIRECT(307, "Temporary Redirect"),//[RFC9110, Section 15.4.8]
    HTTP_308_PERMANENT_REDIRECT(308, "Permanent Redirect"),//[RFC9110, Section 15.4.9]

    HTTP_400_BAD_REQUEST(400, "Bad Request"),//	[RFC9110, Section 15.5.1]
    HTTP_401_UNAUTHORIZED(401, "Unauthorized"),//	[RFC9110, Section 15.5.2]
    HTTP_402_PAYMENT_REQUIRED(402, "Payment Required"),//	[RFC9110, Section 15.5.3]
    HTTP_403_FORBIDDEN(403, "Forbidden"),//	[RFC9110, Section 15.5.4]
    HTTP_404_NOT_FOUND(404, "Not Found"),//	[RFC9110, Section 15.5.5]
    HTTP_405_METHOD_NOT_ALLOWED(405, "Method Not Allowed"),//[RFC9110, Section 15.5.6]
    HTTP_406_NOT_ACCEPTABLE(406, "Not Acceptable"),//	[RFC9110, Section 15.5.7]
    HTTP_407_PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required"),//[RFC9110, Section 15.5.8]
    HTTP_408_REQUEST_TIMEOUT(408, "Request Timeout"),//	[RFC9110, Section 15.5.9]
    HTTP_409_CONFLICT(409, "Conflict"),//[RFC9110, Section 15.5.10]
    HTTP_410_GONE(410, "Gone"),//[RFC9110, Section 15.5.11]
    HTTP_411_LENGTH_REQUIRED(411, "Length Required"),//[RFC9110, Section 15.5.12]
    HTTP_412_PRECONDITION_FAILED(412, "Precondition Failed"),//[RFC9110, Section 15.5.13]
    HTTP_413_CONTENT_TOO_LARGE(413, "Content Too Large"),//[RFC9110, Section 15.5.14]
    HTTP_414_URI_TOO_LONG(414, "URI Too Long"),//[RFC9110, Section 15.5.15]
    HTTP_415_UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),//[RFC9110, Section 15.5.16]
    HTTP_416_RANGE_NOT_SATISFIABLE(416, "Range Not Satisfiable"),//[RFC9110, Section 15.5.17]
    HTTP_417_EXPECTATION_FAILED(417, "Expectation Failed"),//[RFC9110, Section 15.5.18]

    HTTP_421_MISDIRECTED_REQUEST(421, "Misdirected Request"),//[RFC9110, Section 15.5.20]
    HTTP_422_UNPROCESSABLE_CONTENT(422, "Unprocessable Content"), //[RFC9110, Section 15.5.21]
    HTTP_423_LOCKED(423, "Locked"),//[RFC4918]
    HTTP_424_FAILED_DEPENDENCY(424, "Failed Dependency"),//[RFC4918]
    HTTP_425_TOO_EARLY(425, "Too Early"),//[RFC8470]
    HTTP_426_UPGRADE_REQUIRED(426, "Upgrade Required"),//[RFC9110, Section 15.5.22]

    HTTP_428_PRECONDITION_REQUIRED(428, "Precondition Required"),//[RFC6585]
    HTTP_429_TOO_MANY_REQUESTS(429, "Too Many Requests"),//[RFC6585]

    HTTP_431_REQUEST_HEADER_FIELDS_TOO_LARGE(431, "Request Header Fields Too Large"),//[RFC6585]

    HTTP_451_UNAVAILABLE_FOR_LEGAL_REASONS(451, "Unavailable For Legal Reasons"),//[RFC7725]

    HTTP_500_INTERNAL_SERVER_ERROR(500, "Internal Server Error"),//	[RFC9110, Section 15.6.1]
    HTTP_501_NOT_IMPLEMENTED(501, "Not Implemented"),//[RFC9110, Section 15.6.2]
    HTTP_502_BAD_GATEWAY(502, "Bad Gateway"),//[RFC9110, Section 15.6.3]
    HTTP_503_SERVICE_UNAVAILABLE(503, "Service Unavailable"),//[RFC9110, Section 15.6.4]
    HTTP_504_GATEWAY_TIMEOUT(504, "Gateway Timeout"),//	[RFC9110, Section 15.6.5]
    HTTP_505_HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version Not Supported"),//[RFC9110, Section 15.6.6]
    HTTP_506_VARIANT_ALSO_NEGOTIATES(506, "Variant Also Negotiates"),//[RFC2295]
    HTTP_507_INSUFFICIENT_STORAGE(507, "Insufficient Storage"),//[RFC4918]
    HTTP_508_LOOP_DETECTED(508, "Loop Detected"),//[RFC5842]

    HTTP_511_NETWORK_AUTHENTICATION_REQUIRED(511, "Network Authentication Required");    //[RFC6585]

    private int    mCode;
    private String mMessage;

    private HttpResponseStatus(int pCode, String pMessage)
    {
        mCode = pCode;
        mMessage = pMessage;
    }

    public int getCode()
    {
        return mCode;
    }

    public String getMessage()
    {
        return mMessage;
    }

    public static HttpResponseStatus from(int code)
    {
        return Arrays.stream(HttpResponseStatus.values()).filter(e -> e.mCode == code).findAny().orElseThrow();
    }
}
