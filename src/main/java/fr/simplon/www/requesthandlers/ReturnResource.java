package fr.simplon.www.requesthandlers;

import fr.simplon.www.server.IHttpRequest;
import fr.simplon.www.server.IHttpResponse;

import java.io.File;
import java.io.IOException;

/**
 * Retourne le contenu du fichier de ressources demandé dans la requête. Ce fichier doit être dans
 * un répertoire de type "Source resources root" ou "Test Sources Resources Root".
 */
public class ReturnResource extends AbstractHttpRequestHandler
{
    private String mFile;

    /**
     * Constructeur.
     */
    public ReturnResource()
    {
        super();
    }

    /**
     * Constructeur.
     *
     * @param pFile Le chemin du fichier à retourner.
     */
    public ReturnResource(String pFile)
    {
        super();
        mFile = pFile;
    }

    @Override
    public IHttpResponse handle(IHttpRequest pRequest, String pDocumentRoot) throws IOException
    {
        File target = new File(pDocumentRoot, pRequest.getUrl());
        if (mFile != null)
        {
            target = new File(pDocumentRoot, mFile);
        }
        if (target.exists())
        {
            return loadFile(target);
        }
        return loadResource(pRequest.getUrl());
    }
}
