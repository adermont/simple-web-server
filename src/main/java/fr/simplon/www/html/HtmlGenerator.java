package fr.simplon.www.html;

import java.util.ArrayList;
import java.util.List;

/**
 * Générateur de code HTML.
 * <p>
 * Exemple :
 * <pre>
 * HtmlGenerator gen = new HtmlGenerator();
 * HtmlPage htmlPage = gen.title("Any title").lang("en").stylesheet("styles.css").build();
 * htmlPage.p("Hello world!");
 * htmlPage.a("#", "&lt;&lt; Revenir à l'accueil");
 * System.out.println(htmlPage.toHtmlString());
 * </pre>
 */
public class HtmlGenerator
{
    private final List<String> mStylesheet;
    private       String       mTitle;
    private       String       mLang;
    private       String       mCharset;

    /**
     * Constructeur.
     */
    public HtmlGenerator()
    {
        mStylesheet = new ArrayList<>();
    }

    /**
     * Configure le titre de la page.
     *
     * @param pTitle Titre de la page.
     * @return Le même objet.
     */
    public HtmlGenerator title(String pTitle)
    {
        mTitle = pTitle;
        return this;
    }

    /**
     * Configure la langue de la page.
     *
     * @param pLang Langue de la page.
     * @return Le même objet.
     */
    public HtmlGenerator lang(String pLang)
    {
        mLang = pLang;
        return this;
    }

    /**
     * Ajoute une feuille de styles pour la page. Cette méthode peut être appelée plusieurs fois s'il y a plusieurs
     * feuilles de styles.
     *
     * @param pStylesheet Nom et chemin de la feuille de styles.
     * @return Le même objet.
     */
    public HtmlGenerator stylesheet(String pStylesheet)
    {
        mStylesheet.add(pStylesheet);
        return this;
    }

    /**
     * Configure le charset de la page.
     *
     * @param pCharset Charset de la page.
     * @return Le même objet.
     */
    public HtmlGenerator charset(String pCharset)
    {
        mCharset = pCharset;
        return this;
    }

    /**
     * Construit la page.
     *
     * @return Une nouvelle page HTML configurée avec les éléments donnés précédemment avec les méthode
     * {@link #title(String)} , {@link #lang(String)}, {@link #charset(String)} et {@link #stylesheet(String)}.
     */
    public HtmlPage build()
    {
        return new HtmlPage(mTitle, mLang, mCharset, mStylesheet);
    }

}
