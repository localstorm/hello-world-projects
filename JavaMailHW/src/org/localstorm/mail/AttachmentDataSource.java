package org.localstorm.mail;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.activation.DataSource;

/**
 *
 * @author Alexey Kuznetsov
 */
public class AttachmentDataSource implements DataSource 
{

    private EmailAttachment attach;
    
    public AttachmentDataSource(EmailAttachment attach)
    {
        this.attach = attach;
    }
    
    public InputStream getInputStream()
            throws IOException
    {
        return this.attach.getAttachmentBodyAsStream();
    }

    public OutputStream getOutputStream()
            throws IOException
    {
        return null;
    }
    

    public String getContentType()
    {
        return this.attach.getContentType();
    }

    public String getName()
    {
        return this.attach.getAttachmentFileName();
    }
    

}
