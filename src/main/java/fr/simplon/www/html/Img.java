package fr.simplon.www.html;

import java.io.BufferedWriter;
import java.io.IOException;

public class Img extends HtmlElement
{
    private final String mSrc;

    public Img(String pSrc)
    {
        this(null, null, pSrc);
    }

    public Img(String pId, String pStyleClass, String pSrc)
    {
        super("img", pId, pStyleClass);
        mSrc = pSrc;
    }

    @Override
    protected void toHtmlAttributes(BufferedWriter writer) throws IOException
    {
        super.toHtmlAttributes(writer);
        writer.write(String.format(" src=\"%s\"", mSrc));
    }
}
