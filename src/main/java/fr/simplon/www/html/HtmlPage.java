package fr.simplon.www.html;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class HtmlPage extends HtmlElement
{
    private final String       mTitle;
    private final String       mLang;
    private final String       mCharset;
    private final List<String> mStylesheet;

    private final List<HtmlElement> mElements;

    /**
     * Constructeur.
     *
     * @param pTitle      Titre de la page.
     * @param pLang       Langage de la page.
     * @param pCharset    Jeu de caractères de la page.
     * @param pStylesheet La feuille de style à utiliser.
     */
    HtmlPage(String pTitle, String pLang, String pCharset, List<String> pStylesheet)
    {
        super("html", null, null);
        mElements = new ArrayList<>();
        mTitle = pTitle;
        mLang = pLang;
        mCharset = pCharset;
        mStylesheet = new ArrayList<>(pStylesheet);
    }

    @Override
    public <T extends HtmlElement> T add(T e)
    {
        mElements.add(e);
        return e;
    }

    public String toHtmlString()
    {
        StringWriter stringWriter = new StringWriter();
        try (BufferedWriter writer = new BufferedWriter(stringWriter))
        {
            writer.write("<!DOCTYPE html>");
            writer.newLine();
            writer.write(String.format("<html lang=\"%s\">", Optional.ofNullable(mLang).orElse("fr")));
            writer.newLine();
            writer.write("<head>");
            writer.newLine();
            writer.write(String.format("<meta charset=\"%s\">", Optional.ofNullable(mCharset).orElse(StandardCharsets.UTF_8.name())));
            writer.newLine();
            writer.write(String.format("<title>%s</title>", mTitle));
            writer.newLine();
            if (mStylesheet != null)
            {
                writer.write(String.format("<link rel=\"stylesheet\" href=\"%s\"/>", mStylesheet));
                writer.newLine();
            }
            writer.write("</head>");
            writer.newLine();
            writer.write("<body>");

            for (HtmlElement element : mElements)
            {
                writer.write(element.toHtmlString(1));
            }

            writer.write("</body>");
            writer.newLine();
            writer.write("</html>");
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
