package fr.simplon.www.server;

import fr.simplon.www.requesthandlers.ForbiddenResource;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe de gestion des endpoints du serveur.
 */
public class HttpServerEndpoints
{
    private final Map<String, Endpoint> mEndpoints;

    private final Endpoint mFalldown;

    /**
     * Constructeur.
     */
    public HttpServerEndpoints()
    {
        mEndpoints = new HashMap<>();
        mFalldown = new Endpoint("", new ForbiddenResource());
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
