package fr.simplon.www.server;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Implémentation par défaut d'une requête HTTP.
 */
public class HttpRequest implements IHttpRequest
{
    private final HttpMethod          mMethod;
    private final String              mUrl;
    private final Map<String, String> mParams;

    /**
     * Constructeur.
     *
     * @param pMethod La méthode HTTP de la requête.
     * @param pUrl    L'URL de la requête.
     */
    public HttpRequest(HttpMethod pMethod, String pUrl)
    {
        this(pMethod, pUrl, "");
    }

    /**
     * Constructeur.
     *
     * @param pMethod      La méthode HTTP de la requête.
     * @param pUrl         L'URL de la requête.
     * @param pParamString La chaîne de caractère brute des paramètres "après le caractère '?').
     */
    public HttpRequest(HttpMethod pMethod, String pUrl, String pParamString)
    {
        super();
        mMethod = pMethod;
        mUrl = pUrl;
        mParams = parseParams(pParamString);
    }

    /**
     * @param pMethod La méthode HTTP de la requête. * @param pUrl L'URL de la requête.
     * @param pParams Les paramètres dans une map de paires clés/valeurs.
     */
    public HttpRequest(HttpMethod pMethod, String pUrl, Map<String, String> pParams)
    {
        super();
        mMethod = pMethod;
        mUrl = pUrl;
        mParams = pParams;
    }

    /**
     * @return L'URL demandée dans la requête.
     */
    @Override
    public String getUrl()
    {
        return mUrl;
    }

    /**
     * @return La méthode HTTP de la requête.
     */
    @Override
    public HttpMethod getMethod()
    {
        return mMethod;
    }

    /**
     * @param paramName Le nom du paramètre à récupérer.
     * @return La valeur du paramètre.
     */
    public String getParameter(String paramName)
    {
        return mParams.get(paramName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> getParameters()
    {
        return mParams;
    }

    /**
     * Décode la chaîne de caractère brute des paramètres (la partie de l'URL).
     *
     * @param pParamString La chaine brute des paramètres.
     * @return Une map de paires clé/valeur avec les paramètres décodés.
     */
    public static Map<String, String> parseParams(String pParamString)
    {
        Map<String, String> map = new HashMap<>();
        try (Scanner scanner = new Scanner(pParamString))
        {
            scanner.useDelimiter("&");
            while (scanner.hasNext())
            {
                String param = scanner.next();
                int iEgal = param.indexOf('=');
                String pName = param;
                String pValue = "";
                if (iEgal >= 0 && param.length() > 2)
                {
                    pName = param.substring(0, iEgal);
                    pValue = param.substring(iEgal + 1);
                }
                else if (iEgal >= 0)
                {
                    pName = param.substring(0, iEgal);
                }
                map.put(pName, pValue);
            }
        }
        return map;
    }

    /**
     * Ajoute un nouveau paramètre à la requête.
     *
     * @param rawString La chaîne brute correspondant à UN SEUL paramètre (format
     *                  "paramName=paramValue").
     */
    public void addParameter(String rawString)
    {
        int iEgal = rawString.indexOf('=');
        String pName = rawString;
        String pValue = "";
        if (iEgal >= 0 && rawString.length() > 2)
        {
            pName = rawString.substring(0, iEgal);
            pValue = rawString.substring(iEgal + 1);
        }
        else if (iEgal >= 0)
        {
            pName = rawString.substring(0, iEgal);
        }
        mParams.put(pName, pValue);
    }
}
