package fr.simplon.www.html;

import java.io.BufferedWriter;
import java.io.IOException;

public class A extends HtmlElement
{
    private final String mHref;

    public A(String pHref)
    {
        super("a");
        mHref = pHref;
    }

    @Override
    protected void toHtmlAttributes(BufferedWriter writer) throws IOException
    {
        super.toHtmlAttributes(writer);
        writer.write(String.format("href=\"%s\"", mHref));
    }
}
