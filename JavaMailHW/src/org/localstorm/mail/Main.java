package org.localstorm.mail;

import java.io.File;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import java.security.Security;
import java.util.Iterator;
import java.util.Properties;


/**
 * @author localstorm
 */
public class Main {

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
            email.setFromAddress(new InternetAddress(emailFromAddress));    
            email.addAttachment(new FileAttachment(new File("image.jpeg"), "image/jpeg"));
            email.addAttachment(new FileAttachment(new File("build.xml"), "text/xml"));
        }
        
        Main.sendSSLMessage(email, args[0]);   
        System.out.println("Sucessfully Sent mail to All Users");
    }

    public static void sendSSLMessage(final EmailMessage email, final String password) throws MessagingException {
        boolean debug = true;

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
                    return new PasswordAuthentication(email.getFromAddress().toString(), password);
                }
            }
        );

        session.setDebug(debug);
        //-----------------
        
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
            
        // Part two is attachment
        
        
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
        Transport.send(mail);
    }

}
