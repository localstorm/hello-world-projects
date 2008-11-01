package org.localstorm.mail;

import com.sun.mail.pop3.POP3SSLStore;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import java.security.Security;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Properties;


/**
 * @author localstorm
 */
public class Main {
    public static final String INET_ADDRESS_TYPE = "rfc822";

    private static final String SMTP_HOST_NAME = "smtp.gmail.com";
    private static final String SMTP_PORT = "465";
    private static final String emailMsgTxt = "<html><head/><body>Test <b>Пидарас</b> Contents</body></html>";
    private static final String emailSubjectTxt = "A test from gmail";
    private static final String emailFromAddress = "localstorm@gmail.com";
    private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
    private static final String sendTo = "zeextor@gmail.com";


    public static void main(String args[]) throws Exception {
        
        if (args.length!=1)
        {
            System.out.println("Usage: <password>");
            return;
        }
        
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

        EmailMessage email = new EmailMessage();
        {
            email.addRecipient(new InternetAddress(sendTo));
            email.setSubject(emailSubjectTxt);
            email.setMessageText(emailMsgTxt);
            email.setHtmlText(true);
            
            InternetAddress from = new InternetAddress(emailFromAddress);
            from.setPersonal("Педик", "UTF-8");
            
            email.setFromAddress(from);    
            email.addAttachment(new FileAttachment(new File("image.jpeg"), "image/jpeg"));
            email.addAttachment(new FileAttachment(new File("build.xml"), "text/xml"));
        }
        
        //Main.sendSSLMessage(email, args[0]);   
        
        Collection coll = Main.checkNewMessages(args[0]);
        if (!coll.isEmpty())
        {
            for (Iterator it = coll.iterator(); it.hasNext(); )
            {
                EmailMessage msg = (EmailMessage)it.next();
                System.err.println("========================================================");
                System.out.println("Subject: "+msg.getSubject()+"\n"+
                                    "HTML: " + msg.isHtmlText()+"\n"+
                                    msg.getMessageText());
                
                Collection attaches = msg.getAttachments();
                if (attaches.isEmpty())
                {
                    System.out.println("NO ATTACHEMENTS");
                } else {
                    System.out.println("SOME ATTACHEMENTS");
                    for (Iterator ti = attaches.iterator(); ti.hasNext(); )
                    {
                        EmailAttachment attach = (EmailAttachment)ti.next();
                        System.out.println("ATTACH FN: " + attach.getAttachmentFileName() );
                        System.out.println("ATTACH CT: " + attach.getContentType() );
                        InputStream is =attach.getAttachmentBodyAsStream();
                        byte buf[] = new byte[1024];
                        int _rd;
                        
                        while ((_rd = is.read(buf))>=0)
                        {
                            System.out.write(buf, 0, _rd);
                        }
                        
                    }
                    
                }
            }
        } else
        {
            System.out.println("No new messages.");
        }
        
    }

    public static Collection checkNewMessages(String password) 
        throws Exception
    {
        Collection result = new LinkedList();
        Properties props  = new Properties();
        
        props.setProperty("mail.pop3.socketFactory.class",    SSL_FACTORY);
        props.setProperty("mail.pop3.socketFactory.fallback", "false");
        props.setProperty("mail.pop3.port",  "995");
        props.setProperty("mail.pop3.socketFactory.port", "995");
        
        URLName url = new URLName("pop3", "pop.gmail.com", 995, "", "zeextor@gmail.com", password);
        
        Session session = Session.getInstance(props, null);
        POP3SSLStore store = new POP3SSLStore(session, url);
        store.connect();


        Folder folder = store.getDefaultFolder();

        if (folder == null) 
        {
            throw new Exception("No default folder");
        }

        folder = folder.getFolder("INBOX");
        if (folder == null) 
        {
            throw new Exception("No POP3 INBOX");
        }
        
        folder.open(Folder.READ_ONLY);
        
        //System.out.println("COUNT: "+folder.getMessageCount());
        if ( true )
        {    
            Message[] messages = folder.getMessages();
            
            FetchProfile fp = new FetchProfile();
            
            folder.fetch(messages, fp);
            
            for (int i=0; i<messages.length; i++)
            {
                result.add(convertToEmailMessage( messages[i], session ));
            }
        } else 
        {
            System.out.println("HAS NO NEW MESSAGES!");
        }
        
        store.close();
        return result;
    }
    
    
        
    
    public static void sendSSLMessage(final EmailMessage email, final String password) throws Exception {
    
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST_NAME);
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.socketFactory.port", SMTP_PORT);
        props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.put("mail.smtp.socketFactory.fallback", "false");

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("localstorm@gmail.com", password);
                }
            }
        );

        session.setDebug(true);
        
        Transport.send(convertToMimeMessage(email, session));
    }
    
    public static MimeMessage convertToMimeMessage(EmailMessage email, Session session) throws Exception
    {
        // Define message
        MimeMessage mail = new MimeMessage(session);

        mail.setFrom(email.getFromAddress());
        
        for (Iterator it = email.getRecipients().iterator(); it.hasNext(); ) 
        {
            mail.addRecipient( Message.RecipientType.TO, (InternetAddress)it.next());
        }
        
        mail.setSubject( email.getSubject() );

        // create the message part 
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        {
            //fill message
            messageBodyPart.setContent(email.getMessageText(), 
                                           (email.isHtmlText()) 
                                           ? 
                                           "text/html; charset="+email.getCharset() 
                                           : 
                                           "text/plain; charset="+email.getCharset() 
                                       );
            
        }


        MimeBodyPart parts[] = new MimeBodyPart[email.getAttachments().size()];
        
        int i=0;
        for (Iterator it = email.getAttachments().iterator(); it.hasNext(); )
        {
            EmailAttachment attach = (EmailAttachment)it.next();
            parts[i] = new MimeBodyPart();
            {
                DataSource source = new AttachmentDataSource(attach);
                parts[i].setDataHandler(new DataHandler(source));
                parts[i].setFileName(attach.getAttachmentFileName());
            }
            
            i++;
        }
            
        Multipart multipart = new MimeMultipart();
        {
            multipart.addBodyPart(messageBodyPart); // Message text part
            
            for (int j = 0; j<parts.length; j++)
            {
                multipart.addBodyPart(parts[j]);
            }
        }
        
        // Put parts in message
        mail.setContent(multipart);
        return mail;
    }
    
    public static InternetAddress getFirstValidInternetAddress(Address[] addr)
    {
        if (addr==null)
        {
            return null;
        }
        
        for (int i=0; i<addr.length; i++)
        {
            Address address = addr[i];
            if (INET_ADDRESS_TYPE.equals(address.getType()))
            {
                return (InternetAddress) address;
            }
        }
        return null;
    }
    
    public static Collection getInternetAddresses(Address[] addr)
    {
        LinkedList result = new LinkedList();
        if (addr!=null)
        {
            for (int i=0; i<addr.length; i++)
            {
                Address address = addr[i];
                if (INET_ADDRESS_TYPE.equals(address.getType()))
                {
                    result.add((InternetAddress) address);
                }
            }
        }
        
        return result;
    }
    
    public static EmailMessage convertToEmailMessage(Message email, Session session) throws Exception
    {
        // Define message
        EmailMessage mail = new EmailMessage();

        mail.setFromAddress( getFirstValidInternetAddress(email.getFrom()) );
        
        Address[] recip = email.getAllRecipients();
        
        for (Iterator it = getInternetAddresses(recip).iterator(); it.hasNext(); ) 
        {
            mail.addRecipient( (InternetAddress)it.next() );
        }
        
        mail.setSubject( email.getSubject() );
        mail.setSentDate(email.getSentDate());
        mail.setRecievedDate(email.getReceivedDate());

        Object content = email.getContent();
        if (content instanceof Multipart) 
        {
            handleMultipart( (Multipart)content, mail );
        } else if (content instanceof Part)
        {
            handlePart( (Part)content, mail );
        } else if (content instanceof String)
        {
            mail.setMessageText((String)content);
            mail.setHtmlText(isHtmlByContentType(email.getContentType()));
        } else 
        {
            throw new Exception("Unexpected content type");
        }

        return mail;
    }

    public static void handleMultipart(Multipart multipart, EmailMessage mail) 
        throws Exception 
    {
        for (int i=0, n=multipart.getCount(); i<n; i++) 
        {
            handlePart(multipart.getBodyPart(i), mail);
        }
    }

    private static void handlePart(Part part, EmailMessage mail) throws Exception
    {
        String disposition = part.getDisposition();
        String contentType = part.getContentType();
        ContentType ct = new ContentType(contentType);
        String baseType = ct.getBaseType();
        
        if (disposition == null) 
        {   // When just body
            
            if (!baseType.startsWith("text/"))
            {
                throw new MessagingException("Textual mime-type expected.");
            }
            
            mail.setHtmlText(isHtmlByContentType(ct));
            mail.setMessageText(partAsString(part, ct));
            
        } else if (disposition.equalsIgnoreCase(Part.ATTACHMENT) || disposition.equalsIgnoreCase(Part.INLINE)) 
        {
            mail.addAttachment(new BinaryAttachment(partAsBinary(part), part.getFileName(), baseType));
        } else 
        {
            // Should never happen
            throw new MessagingException("Unexpected e-mail message format.");
        }
    }

    private static boolean isHtmlByContentType(String cont) throws Exception
    {
        ContentType ct = new ContentType(cont);
        return isHtmlByContentType(ct);
    }
    
    private static boolean isHtmlByContentType(ContentType ct) throws Exception
    {
        return (ct.getBaseType().equals("text/html"));
    }

    private static byte[] partAsBinary(Part part) throws Exception
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try
        {
            writeTo(part, baos);
            
        } finally
        {
            baos.close();    
        }
        return baos.toByteArray();
    }

    private static String partAsString(Part part, ContentType ct) throws Exception
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try
        {
            writeTo(part, baos);
        } finally
        {
            baos.close();    
        }
        
        String cs = ct.getParameter("charset");
        if (cs!=null)
        {
            return baos.toString(cs);
        } else 
        {
            return baos.toString();
        }
    }

    private static void writeTo(Part part, OutputStream baos)
            throws IOException,
                   MessagingException,
                   IOException
    {
        InputStream is = part.getInputStream();
        byte[] buf     = new byte[1024];
        int _rd;

        while ((_rd = is.read(buf)) >= 0)
        {
            baos.write(buf, 0, _rd);
        }
    }
}
