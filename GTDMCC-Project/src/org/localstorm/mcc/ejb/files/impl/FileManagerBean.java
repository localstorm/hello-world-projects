package org.localstorm.mcc.ejb.files.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;
import org.localstorm.mcc.ejb.dao.FileDao;
import org.localstorm.mcc.ejb.Constants;
import org.localstorm.mcc.ejb.files.FileAttachment;
import org.localstorm.mcc.ejb.files.FileManagerLocal;
import org.localstorm.mcc.ejb.referenced.ReferencedObject;

/**
 *
 * @author Alexey Kuznetsov
 */
@Stateless 
public class FileManagerBean implements FileManagerLocal
{
    @Resource(mappedName=Constants.DEFAULT_DS)
    private DataSource ds;
    
    public FileManagerBean()
    {
    }
    
    @Override
    public void attachToObject(FileAttachment fa, ReferencedObject ro, InputStream is)
    {
        FileDao fileDao = new FileDao(ds);

        try {
            em.persist(fa);
            Integer fileId = fa.getId();

            FileToRefObject link = new FileToRefObject(fa, ro);
            em.persist(link);

            fileDao.uploadFile(is, fileId);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Collection<FileAttachment> findAllAttachmentsByObject(ReferencedObject ro) {
        Query uq = em.createNamedQuery(FileToRefObject.Queries.FIND_FILES_BY_OBJECT);
        uq.setParameter(FileToRefObject.Properties.OBJECT, ro);  
        
        List<FileAttachment> list = uq.getResultList();
        System.out.println("RETURNED: "+list.size());
        return list;
    }

    @Override
    public FileAttachment findAttachmentById(Integer id) 
    {
        return em.find(FileAttachment.class, id);
    }
    
    @Override
    public void download(FileAttachment fa, OutputStream os) throws IOException 
    {
        try {
            Integer fileId = fa.getId();
            FileDao fileDao = new FileDao(ds);
            fileDao.downloadFile(os, fileId);
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void detach(FileAttachment fa, ReferencedObject ro) 
    {
        try {
            FileDao fileDao = new FileDao(ds);
            fileDao.deleteBody(fa.getId());

            Query uq = em.createNamedQuery(FileToRefObject.Queries.FIND_BY_FILE);
            uq.setParameter(FileToRefObject.Properties.FILE, fa);  
            List<FileToRefObject> links = (List<FileToRefObject>) uq.getResultList();
            
            for (FileToRefObject fto: links) {
                em.remove(fto);
            }
            
            em.remove(em.find(FileAttachment.class, fa.getId()));
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @PersistenceContext(unitName=Constants.DEFAULT_PU)
    protected EntityManager em;
}
