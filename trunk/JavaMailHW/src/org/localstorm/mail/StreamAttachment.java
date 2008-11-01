package org.localstorm.mail;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Alexey Kuznetsov
 */
public class StreamAttachment implements EmailAttachment
{
    private final InputStream is;
    private final String name;
    private final String contentType;
    
    
    public StreamAttachment(InputStream is, String name, String contentType)
    {
        if (name==null || is==null)
        {
            throw new NullPointerException("Given name/stream is null!");
        }
        
        this.is = is;
        this.name = name;
        this.contentType = contentType;
    }

    public InputStream getAttachmentBodyAsStream()
            throws IOException
    {
        return this.is;
    }

    public String getAttachmentFileName()
    {
        return this.name;
    }

    public String getContentType()
    {
        return this.contentType;
    }
    

}
