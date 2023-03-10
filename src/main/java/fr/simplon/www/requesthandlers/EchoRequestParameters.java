package fr.simplon.www.requesthandlers;

import fr.simplon.www.html.HtmlGenerator;
import fr.simplon.www.html.HtmlPage;
import fr.simplon.www.server.HttpResponse;
import fr.simplon.www.server.IHttpRequest;
import fr.simplon.www.server.IHttpResponse;

import java.util.Map;
import java.util.Set;

/**
 * Renvoie les paramètres d'une requête dans la réponse.
 */
public class EchoRequestParameters extends AbstractHttpRequestHandler
{
    /**
     * Renvoie les paramètres d'une requête dans la réponse.
     *
     * @param request Requête.
     * @return Les paramètres de la requête, formatés en mode 1 param / ligne.
     */
    @Override
    public IHttpResponse handle(IHttpRequest request, String pDocumentRoot)
    {
        HtmlGenerator gen = new HtmlGenerator();
        HtmlPage htmlPage = gen.title("Echo service").lang("fr").stylesheet("styles.css").build();

        htmlPage.p("Requête : " + request.toString());

        // Récupération des paramètres de la requête
        Map<String, String> parameters = request.getParameters();
        Set<Map.Entry<String, String>> entries = parameters.entrySet();

        // Pour chaque paramètre de la requete, on ajoute un nouveau paragraphe
        for (Map.Entry<String, String> entry : entries)
        {
            htmlPage.p(String.valueOf(entry));
        }
        htmlPage.a("#", "&lt;&lt; Revenir à l'accueil");

        return HttpResponse.textHtml(htmlPage.toHtmlString().getBytes());
    }
}
