package org.localstorm.mail;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import javax.mail.internet.InternetAddress;

/**
 * @author Alexey Kuznetsov
 */
public class EmailMessage 
{
    private Collection      toAddresses = new LinkedList();
    private Collection      attachments = new LinkedList();

    private InternetAddress fromAddress;
    private String          subject;
    private String          message;
    private boolean         htmlText;
    private Date            recievedDate;
    private Date            sentDate;
    
    public EmailMessage()
    {
        this.htmlText = false;
    }

    
    public Collection getAttachments()
    {
        return Collections.unmodifiableCollection(this.attachments);
    }

    public void addAttachment(EmailAttachment attach)
    {
        if (attach==null)
        {
            throw new NullPointerException("Given attachment is null!");
        }
        
        this.attachments.add(attach);
    }

    
    public InternetAddress getFromAddress()
    {
        return this.fromAddress;
    }

    public void setFromAddress(InternetAddress fromAddress)
    {
        this.fromAddress = fromAddress;
    }

    public String getSubject()
    {
        return this.subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public Collection getRecipients()
    {
        return Collections.unmodifiableCollection(this.toAddresses);
    }
    
    public void addRecipient(InternetAddress address)
    {
        if (address==null)
        {
            throw new NullPointerException("Given address is null!");
        }
        
        this.toAddresses.add(address);
    }
    
    public boolean isHtmlText()
    {
        return this.htmlText;
    }

    public void setHtmlText(boolean htmlText)
    {
        this.htmlText = htmlText;
    }

    public Date getRecievedDate()
    {
        return recievedDate;
    }

    public void setRecievedDate(Date recievedDate)
    {
        this.recievedDate = recievedDate;
    }
    
    public Date getSentDate()
    {
        return sentDate;
    }
    
    public void setSentDate(Date sentDate)
    {
        this.sentDate = sentDate;
    }

    public void setMessageText(String emailMsgTxt)
    {
        this.message = emailMsgTxt;
    }
    
    public String getMessageText()
    {
        return this.message;
    }
    
}
