package org.localstorm.mcc.ejb.files;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import org.localstorm.mcc.ejb.referenced.ReferencedObject;

/**
 *
 * @author Alexey Kuznetsov
 */
public interface FileManager 
{
    public static final String BEAN_NAME = "FileManagerBean";
    
    public void download(FileAttachment fa, OutputStream os) throws IOException;
    public void attachToObject(FileAttachment fa, ReferencedObject ro, InputStream is);
    public void detach(FileAttachment fa, ReferencedObject ro);
    
    public Collection<FileAttachment> findAllAttachmentsByObject(ReferencedObject ro);
    public FileAttachment findAttachmentById(Integer id);
    
    
}
