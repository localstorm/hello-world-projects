package org.localstorm.mail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Alexey Kuznetsov
 */
public class BinaryAttachment implements EmailAttachment
{
    private byte[] body;
    private String fileName;
    private String contentType;
    
    public BinaryAttachment(byte[] body, String fileName, String contentType)
    {
        this.body = body;
        this.fileName = fileName;
        this.contentType = contentType;
    }
    
    public InputStream getAttachmentBodyAsStream()
            throws IOException
    {
        return new ByteArrayInputStream(this.body);
    }

    public String getContentType()
    {
        return this.contentType;
    }

    public String getAttachmentFileName()
    {
        return this.fileName;
    }

}
