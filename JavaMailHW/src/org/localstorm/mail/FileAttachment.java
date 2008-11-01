package org.localstorm.mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Alexey Kuznetsov
 */
public class FileAttachment implements EmailAttachment
{
    private final File file;
    private final String type;
    
    public FileAttachment(File file, String contentType)
    {
        if (file==null)
        {
            throw new NullPointerException("Given file is null!");
        }
        this.file = file;
        this.type = contentType;
    }
    
    public InputStream getAttachmentBodyAsStream() 
        throws IOException
    {
        return new FileInputStream(this.file);
    }

    public String getAttachmentFileName()
    {
        return this.file.getName();
    }

    public String getContentType()
    {
        return this.type;
    }
    
}
