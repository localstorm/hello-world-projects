package org.localstorm.mail;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Alexey Kuznetsov
 */
public interface EmailAttachment 
{
    public InputStream getAttachmentBodyAsStream() throws IOException;
    public String getAttachmentFileName();
    public String getContentType();
}
