package fr.simplon.www.requesthandlers;

import fr.simplon.www.server.HttpResponse;
import fr.simplon.www.server.HttpResponseStatus;
import fr.simplon.www.server.IHttpRequest;
import fr.simplon.www.server.IHttpResponse;

import java.io.IOException;

public class ForbiddenResource extends AbstractHttpRequestHandler
{
    @Override
    public IHttpResponse handle(IHttpRequest pRequest, String pDocumentRoot) throws IOException
    {
        return HttpResponse.error(HttpResponseStatus.HTTP_403_FORBIDDEN, pRequest.getUrl().getBytes());
    }
}
