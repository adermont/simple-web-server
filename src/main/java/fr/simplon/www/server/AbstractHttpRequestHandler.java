package fr.simplon.www.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Classe de base pour l'implémentation du traitement d'une requête.
 */
public abstract class AbstractHttpRequestHandler implements IHttpRequestHandler
{
    private static System.Logger logger = System.getLogger(AbstractHttpRequestHandler.class.getName());

    /**
     * {@inheritDoc}
     */
    @Override
    public IHttpResponse handle(IHttpRequest pRequest, String pDocumentRoot) throws IOException
    {
        return loadResource(pDocumentRoot + pRequest.getUrl());
    }

    /**
     * Charge un fichier HTML depuis les ressources du projet. Le fichier doit nécessairement se
     * trouver embarqué dans l'archive JAR du module simple-web-server ou dans le classpath des
     * ressources du programme appelant.
     *
     * @param resourceUrl L'URL du fichier ressource à charger.
     * @return La réponse HTTP (ContentType=text/html) dont le corps est le contenu du fichier
     *         HTML.
     *
     * @throws Exception En cas de problème de lecture du fichier.
     */
    protected IHttpResponse loadResource(String resourceUrl) throws IOException
    {
        try (InputStream stream = getClass().getResourceAsStream(resourceUrl))
        {
            if (stream != null)
            {
                String content = new String(stream.readAllBytes());
                return toResponse(content, resourceUrl);
            }
            File file = new File(resourceUrl);
            if (file.exists())
            {
                return loadFile(file);
            }
            logger.log(System.Logger.Level.TRACE, "Fichier introuvable : {0}", resourceUrl);
            return HttpResponse.error(HttpResponseStatus.HTTP_404_NOT_FOUND, resourceUrl);
        }
    }

    /**
     * Charge un fichier HTML depuis le disque. Le fichier doit se trouver à la racine d'un
     * répertoire source.
     *
     * @param pFile Le fichier à charger.
     * @return La réponse HTTP (ContentType=text/html) dont le corps est le contenu du fichier
     *         HTML.
     *
     * @throws Exception En cas de problème de lecture du fichier.
     */
    protected IHttpResponse loadFile(File pFile) throws IOException
    {
        if (pFile.exists())
        {
            try (InputStream stream = new FileInputStream(pFile))
            {

                String content = new String(stream.readAllBytes());
                return toResponse(content, pFile.getPath());
            }
        }
        else {
            logger.log(System.Logger.Level.TRACE, "Fichier introuvable : {0}", pFile);
            return HttpResponse.error(HttpResponseStatus.HTTP_404_NOT_FOUND, pFile.getPath());
        }
    }

    /**
     * Crée une nouvelle HttpResponse à partir de l'extension du fichier demandé.
     *
     * @param content     Contenu du fichier sous forme d'une String.
     * @param resourceUrl URL de la ressource demandée.
     * @return Une HttpResponse dont le content-type correspond à l'extension de fichier demandé.
     */
    protected IHttpResponse toResponse(String content, String resourceUrl)
    {
        int indexOfDot = resourceUrl.lastIndexOf('.') + 1;
        if (indexOfDot > 0)
        {
            String fileExtension = resourceUrl.substring(indexOfDot);
            return HttpResponse.fromFileExtension(fileExtension, content);
        }
        else
        {
            logger.log(System.Logger.Level.TRACE, "Extension de fichier inconnue : {0}", resourceUrl);
            return HttpResponse.textPlain(content);
        }
    }
}
