package fr.simplon.www.server;

import java.io.IOException;

/**
 * Un endpoint est un point d'entrée du serveur HTTP, accessible à partir d'une URL. Quand un
 * navigateur demande une URL qui correspond à celle d'un endpoint, il est activé et le contenu qui
 * sera renvoyé au navigateur sera le retour de la méthode
 * {@link IHttpRequestHandler#handle(IHttpRequest, String)}.
 */
public class Endpoint
{
    private       String              mDocumentRoot;
    private final String              mUrl;
    private final IHttpRequestHandler mHandler;

    /**
     * Le point d'entrée.
     *
     * @param pUrl  L'URL du endpoint.
     * @param pHandler Le handler qui traite la requête.
     */
    public Endpoint(String pUrl, IHttpRequestHandler pHandler)
    {
        mUrl = pUrl;
        mHandler = pHandler;
    }

    /**
     * @return L'URL à laquelle répondra le endpoint.
     */
    public String getUrl()
    {
        return mUrl;
    }

    /**
     * Retourne <code>true</code> si la requête fournie en paramètre peut être traitée par cet
     * endpoint.
     *
     * @param request La requête à tester.
     * @return <code>true</code> si la requête fournie en paramètre peut être traitée
     *         par cet endpoint.
     */
    public boolean matches(IHttpRequest request)
    {
        return mUrl != null && request.getUrl() != null && request.getUrl().startsWith(mUrl);
    }

    /**
     * @return Racine des documents servis par le serveur.
     */
    public String getDocumentRoot()
    {
        return mDocumentRoot;
    }

    /**
     * Modifie la racine des documents HTML.
     *
     * @param pDocumentRoot Racine des documents servis par le serveur.
     */
    public void setDocumentRoot(String pDocumentRoot)
    {
        mDocumentRoot = pDocumentRoot;
    }

    /**
     * Traite une requête.
     *
     * @param request La requête à traiter.
     * @return Le nouvelle requête.
     *
     * @throws IOException En cas de problèmle de traitement de la requête.
     */
    public IHttpResponse process(IHttpRequest request) throws IOException
    {
        return mHandler.handle(request, mDocumentRoot);
    }
}
