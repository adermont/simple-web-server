package fr.simplon.www.html;

public class Text extends HtmlElement
{
    public Text(String pText)
    {
        super(pText, null, null);
    }

    @Override
    public String toHtmlString(int level)
    {
        return getName();
    }
}
