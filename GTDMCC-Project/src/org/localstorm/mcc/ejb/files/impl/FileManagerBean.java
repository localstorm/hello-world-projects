package org.localstorm.mcc.ejb.files.impl;

import java.io.InputStream;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    @Resource(mappedName="java:MCC_DB")
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
            System.err.println("FUCK!");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    
    @PersistenceContext(unitName=Constants.DEFAULT_PU)
    protected EntityManager em;
}
