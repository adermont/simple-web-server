package fr.simplon.www.server;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe de gestion des endpoints du serveur.
 */
public class HttpServerEndpoints
{
    private Map<String, Endpoint> mEndpoints;

    private Endpoint mFalldown;

    /**
     * Constructeur.
     */
    public HttpServerEndpoints()
    {
        mEndpoints = new HashMap<>();
        mFalldown = new Endpoint("", new AbstractHttpRequestHandler() {});
    }

    /**
     * Pour ajouter un nouveau endpoint.
     *
     * @param endpoint un nouveau endpoint.
     */
    public void addEndpoint(Endpoint endpoint)
    {
        mEndpoints.put(endpoint.getUrl(), endpoint);
    }

    /**
     * Cette méthode permet de récupérer le endpoint qui correspond à une URL. Si aucun endpoint ne
     * correspond, la méthode retournera un endpoint de "falldown" (c'est à dire par défaut).
     *
     * @param pUrl L'URL à tester.
     * @return le endpoint correspond à cette URL.
     */
    public Endpoint match(String pUrl)
    {
        Endpoint endpoint = mEndpoints.get(pUrl);
        if (endpoint == null)
        {
            endpoint = mFalldown;
        }
        return endpoint;
    }
}
