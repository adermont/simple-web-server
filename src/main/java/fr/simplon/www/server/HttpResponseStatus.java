package fr.simplon.www.server;

import java.util.Arrays;

/**
 * Les différents types de status définis par le protocole HTTP.
 */
public enum HttpResponseStatus
{
    HTTP_100_Continue(100, "Continue"),//	[RFC9110, Section 15.2.1]
    HTTP_101_Switching_Protocols(101, "Switching Protocols"),//[RFC9110, Section 15.2.2]
    HTTP_102_Processing(102, "Processing"),//[RFC2518]
    HTTP_103_Early_Hints(103, "Early Hints"),//[RFC8297]

    HTTP_200_OK(200, "OK"),    //[RFC9110, Section 15.3.1]
    HTTP_201_Created(201, "Created"),//[RFC9110, Section 15.3.2]
    HTTP_202_Accepted(202, "Accepted"),//[RFC9110, Section 15.3.3]
    HTTP_203_Non_Authoritative_Information(203, "Non Authoritative Information"),//[RFC9110, Section 15.3.4]
    HTTP_204_No_Content(204, "No Content"),//[RFC9110, Section 15.3.5]
    HTTP_205_Reset_Content(205, "Reset Content"),//[RFC9110, Section 15.3.6]
    HTTP_206_Partial_Content(206, "Partial Content"),//	[RFC9110, Section 15.3.7]
    HTTP_207_Multi_Status(207, "Multi Status"),//[RFC4918]
    HTTP_208_Already_Reported(208, "Already Reported"),//[RFC5842]

    HTTP_226_IM_Used(226, "IM Used"),//[RFC3229]

    HTTP_300_Multiple_Choices(300, "Multiple Choices"),//[RFC9110, Section 15.4.1]
    HTTP_301_Moved_Permanently(301, "Moved Permanently"),//[RFC9110, Section 15.4.2]
    HTTP_302_Found(302, "Found"),//[RFC9110, Section 15.4.3]
    HTTP_303_See_Other(303, "See Other"),//[RFC9110, Section 15.4.4]
    HTTP_304_Not_Modified(304, "Not Modified"),//[RFC9110, Section 15.4.5]
    HTTP_305_Use_Proxy(305, "Use Proxy"),//[RFC9110, Section 15.4.6]
    HTTP_307_Temporary_Redirect(307, "Temporary Redirect"),//[RFC9110, Section 15.4.8]
    HTTP_308_Permanent_Redirect(308, "Permanent Redirect"),//[RFC9110, Section 15.4.9]

    HTTP_400_Bad_Request(400, "Bad Request"),//	[RFC9110, Section 15.5.1]
    HTTP_401_Unauthorized(401, "Unauthorized"),//	[RFC9110, Section 15.5.2]
    HTTP_402_Payment_Required(402, "Payment Required"),//	[RFC9110, Section 15.5.3]
    HTTP_403_Forbidden(403, "Forbidden"),//	[RFC9110, Section 15.5.4]
    HTTP_404_Not_Found(404, "Not Found"),//	[RFC9110, Section 15.5.5]
    HTTP_405_Method_Not_Allowed(405, "Method Not Allowed"),//[RFC9110, Section 15.5.6]
    HTTP_406_Not_Acceptable(406, "Not Acceptable"),//	[RFC9110, Section 15.5.7]
    HTTP_407_Proxy_Authentication_Required(407, "Proxy Authentication Required"),//[RFC9110, Section 15.5.8]
    HTTP_408_Request_Timeout(408, "Request Timeout"),//	[RFC9110, Section 15.5.9]
    HTTP_409_Conflict(409, "Conflict"),//[RFC9110, Section 15.5.10]
    HTTP_410_Gone(410, "Gone"),//[RFC9110, Section 15.5.11]
    HTTP_411_Length_Required(411, "Length Required"),//[RFC9110, Section 15.5.12]
    HTTP_412_Precondition_Failed(412, "Precondition Failed"),//[RFC9110, Section 15.5.13]
    HTTP_413_Content_Too_Large(413, "Content Too Large"),//[RFC9110, Section 15.5.14]
    HTTP_414_URI_Too_Long(414, "URI Too Long"),//[RFC9110, Section 15.5.15]
    HTTP_415_Unsupported_Media_Type(415, "Unsupported Media Type"),//[RFC9110, Section 15.5.16]
    HTTP_416_Range_Not_Satisfiable(416, "Range Not Satisfiable"),//[RFC9110, Section 15.5.17]
    HTTP_417_Expectation_Failed(417, "Expectation Failed"),//[RFC9110, Section 15.5.18]

    HTTP_421_Misdirected_Request(421, "Misdirected Request"),//[RFC9110, Section 15.5.20]
    HTTP_422_Unprocessable_Content(422, "Unprocessable Content"), //[RFC9110, Section 15.5.21]
    HTTP_423_Locked(423, "Locked"),//[RFC4918]
    HTTP_424_Failed_Dependency(424, "Failed Dependency"),//[RFC4918]
    HTTP_425_Too_Early(425, "Too Early"),//[RFC8470]
    HTTP_426_Upgrade_Required(426, "Upgrade Required"),//[RFC9110, Section 15.5.22]

    HTTP_428_Precondition_Required(428, "Precondition Required"),//[RFC6585]
    HTTP_429_Too_Many_Requests(429, "Too Many Requests"),//[RFC6585]

    HTTP_431_Request_Header_Fields_Too_Large(431, "Request Header Fields Too Large"),//[RFC6585]

    HTTP_451_Unavailable_For_Legal_Reasons(451, "Unavailable For Legal Reasons"),//[RFC7725]

    HTTP_500_Internal_Server_Error(500, "Internal Server Error"),//	[RFC9110, Section 15.6.1]
    HTTP_501_Not_Implemented(501, "Not Implemented"),//[RFC9110, Section 15.6.2]
    HTTP_502_Bad_Gateway(502, "Bad Gateway"),//[RFC9110, Section 15.6.3]
    HTTP_503_Service_Unavailable(503, "Service Unavailable"),//[RFC9110, Section 15.6.4]
    HTTP_504_Gateway_Timeout(504, "Gateway Timeout"),//	[RFC9110, Section 15.6.5]
    HTTP_505_HTTP_Version_Not_Supported(505, "HTTP Version Not Supported"),//[RFC9110, Section 15.6.6]
    HTTP_506_Variant_Also_Negotiates(506, "Variant Also Negotiates"),//[RFC2295]
    HTTP_507_Insufficient_Storage(507, "Insufficient Storage"),//[RFC4918]
    HTTP_508_Loop_Detected(508, "Loop Detected"),//[RFC5842]

    HTTP_511_Network_Authentication_Required(511, "Network Authentication Required");    //[RFC6585]

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
