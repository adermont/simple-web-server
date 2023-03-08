package fr.simplon.www.server;

/**
 * Un endpoint est un point d'entrée du serveur HTTP, accessible à partir d'une URL. Quand un
 * navigateur demande une URL qui correspond à celle d'un endpoint, il est activé et le contenu qui
 * sera renvoyé au navigateur sera le retour de la méthode
 * {@link IHttpRequestHandler#handle(IHttpRequest)}.
 */
public class Endpoint
{
    private String              mUrl;
    private String              mDocumentRoot;
    private IHttpRequestHandler mHandler;

    /**
     * Le point d'entrée.
     *
     * @param pUrl
     * @param pHandler
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
     * @throws Exception
     */
    public IHttpResponse process(IHttpRequest request) throws Exception
    {
        return mHandler.handle(request, mDocumentRoot);
    }
}
