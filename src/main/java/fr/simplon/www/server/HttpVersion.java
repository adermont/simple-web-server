package fr.simplon.www.server;

import java.util.Arrays;
import java.util.Optional;

public enum HttpVersion
{
    HTTP_1_0("HTTP/1.0"),
    HTTP_1_1("HTTP/1.1"),
    HTTP_2_0("HTTP/2.0"),
    HTTP_3_0("HTTP/3.0");

    private final String mVersion;

    HttpVersion(String pVersion)
    {
        mVersion = pVersion;
    }

    public String toHttpString()
    {
        return mVersion;
    }

    @Override
    public String toString()
    {
        return toHttpString();
    }

    public static Optional<HttpVersion> from(String sProtocolVersion)
    {
        return Arrays.stream(HttpVersion.values()).filter(v -> v.toHttpString().equals(sProtocolVersion)).findAny();
    }
}
