package fr.simplon.www.requesthandlers;

import fr.simplon.www.server.AbstractHttpRequestHandler;
import fr.simplon.www.server.HttpResponse;
import fr.simplon.www.server.IHttpRequest;
import fr.simplon.www.server.IHttpResponse;

import java.util.Map;
import java.util.Set;

public class EchoRequestParameters extends AbstractHttpRequestHandler
{
    /**
     * @param request
     * @return
     */
    @Override
    public IHttpResponse handle(IHttpRequest request) throws Exception
    {
        String newLine = System.lineSeparator();
        StringBuilder sb = new StringBuilder("<html><body>").append(newLine);

        Map<String, String> parameters = request.getParameters();
        Set<Map.Entry<String, String>> entries = parameters.entrySet();
        for (Map.Entry<String, String> entry : entries)
        {
            sb.append(entry).append("<br/>").append(newLine);
        }
        sb.append("<p><a href=\"/\">&lt;&lt; Revenir à l'accueil</a></p>").append(newLine);
        sb.append("</body></html>").append(newLine);
        return HttpResponse.textHtml(sb.toString());
    }
}
