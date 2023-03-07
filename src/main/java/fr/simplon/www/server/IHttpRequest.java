package fr.simplon.www.server;

import java.util.Map;

/**
 * Requête HTTP.
 */
public interface IHttpRequest
{
    /**
     * @return La méthode HTTP utilisée pour faire la requête (GET, POST, PUT, ...).
     */
    HttpMethod getMethod();

    /**
     * @return L'URL de la requête.
     */
    String getUrl();

    /**
     * Les paramètres de la requête.
     * @param paramName Le nom du paramètre.
     * @return Le paramètre dont le nom est 'paramName'.
     */
    String getParameter(String paramName);

    /**
     * @return Tous les paramètres de la requête.
     */
    Map<String,String> getParameters();
}
