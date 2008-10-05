package org.localstorm.mcc.ejb.files;

import java.io.InputStream;
import org.localstorm.mcc.ejb.referenced.ReferencedObject;

/**
 *
 * @author Alexey Kuznetsov
 */
public interface FileManager 
{
    public static final String BEAN_NAME = "FileManagerBean";
    
    public void attachToObject(FileAttachment fa, ReferencedObject ro, InputStream is);
    
    
}
