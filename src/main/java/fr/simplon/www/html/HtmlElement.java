package fr.simplon.www.html;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public abstract class HtmlElement
{
    private final List<HtmlElement> mChildren;
    private final String            mStyleClass;
    private final String            mName;
    private       String            mId;

    protected HtmlElement(String pName)
    {
        this(pName, null, null);
    }

    protected HtmlElement(String pName, String pId, String pClass)
    {
        mName = pName;
        mId = pId;
        mStyleClass = pClass;
        mChildren = new ArrayList<>();
    }

    public String getName()
    {
        return mName;
    }

    public String getId()
    {
        return mId;
    }

    public void setId(String pId)
    {
        mId = pId;
    }

    public String getStyleClass()
    {
        return mStyleClass;
    }

    public <T extends HtmlElement> T add(T pHtmlElement)
    {
        mChildren.add(pHtmlElement);
        return pHtmlElement;
    }

    public List<HtmlElement> getChildren()
    {
        return mChildren;
    }

    public Div div()
    {
        return add(new Div());
    }

    public Div div(String pId, String pStyleClass)
    {
        return add(new Div(pId, pStyleClass));
    }

    public A a(String href, String pText)
    {
        A a = new A(href);
        a.text(pText);
        return a;
    }

    public P p()
    {
        return add(new P());
    }

    public P p(String text)
    {
        P p = add(new P());
        p.add(new Text(text));
        return p;
    }

    public Br br()
    {
        return add(new Br());
    }

    public Text text(String pText)
    {
        return add(new Text(pText));
    }

    public Img img(String pText)
    {
        return add(new Img(null, "", pText));
    }

    protected void toHtmlAttributes(BufferedWriter writer) throws IOException
    {
        if (getId() != null)
        {
            writer.append(String.format(" id=\"%s\"", getId()));
        }
        if (getStyleClass() != null)
        {
            writer.append(String.format(" class=\"%s\"", getStyleClass()));
        }
    }

    public String toHtmlString(int level)
    {
        StringWriter stringWriter = new StringWriter();
        try (BufferedWriter writer = new BufferedWriter(stringWriter))
        {
            writer.append(String.format("%s<%s", "  ".repeat(level), getName()));

            toHtmlAttributes(writer);

            if (!mChildren.isEmpty())
            {
                writer.append('>');
                writer.newLine();
                for (HtmlElement child : getChildren())
                {
                    writer.append(child.toHtmlString(level + 1));
                }
                writer.append(String.format("</%s>", getName()));
            }
            else
            {
                writer.append("/>");
            }
            writer.newLine();
            writer.flush();
        }
        catch (IOException pE)
        {
            pE.printStackTrace();
        }
        return stringWriter.toString();
    }
}
