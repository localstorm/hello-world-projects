package org.localstorm.mail;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import java.security.Security;
import java.util.Properties;


/**
 * @author localstorm
 */
public class Main {

    private static final String SMTP_HOST_NAME = "smtp.gmail.com";
    private static final String SMTP_PORT = "465";
    private static final String emailMsgTxt = "Test Message Contents";
    private static final String emailSubjectTxt = "A test from gmail";
    private static final String emailFromAddress = "localstorm@gmail.com";
    private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
    private static final String[] sendTo = {"zeextor@gmail.com"};


    public static void main(String args[]) throws Exception {
        
        if (args.length!=1)
        {
            System.out.println("Usage: <password>");
            return;
        }
        
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

        new Main().sendSSLMessage(sendTo, emailSubjectTxt, emailMsgTxt, emailFromAddress, args[0]);   
        System.out.println("Sucessfully Sent mail to All Users");
    }

    public void sendSSLMessage(String recipients[], String subject,
        String message, final String from, final String password) throws MessagingException {
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
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from, password);
                }
            }
        );

        session.setDebug(debug);
        //-----------------
        
         // Define message
        MimeMessage mail = new MimeMessage(session);
        mail.setFrom(new InternetAddress(from));
        
        for (int i = 0; i < recipients.length; i++) {
            mail.addRecipient( Message.RecipientType.TO, new InternetAddress(recipients[i]));
        }
        
        mail.setSubject( "Hello JavaMail Attachment" );

        // create the message part 
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        {
            //fill message
            messageBodyPart.setText(message);
        }

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        // Part two is attachment
        messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource("image.jpeg");
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName("cool.jpeg");
        multipart.addBodyPart(messageBodyPart);

        // Put parts in message
        mail.setContent(multipart);
        Transport.send(mail);
        
    }

}
