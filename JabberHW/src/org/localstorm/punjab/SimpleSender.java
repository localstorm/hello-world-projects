package org.localstorm.punjab;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Properties;
import org.jivesoftware.smack.ConnectionConfiguration;

/**
 *
 * @author Alexey Kuznetsov
 */
public class SimpleSender 
{
    public static void main(String[] args) {
        
        if (args.length!=3)
        {
            System.err.println("Usage: <settings-file> <recipient@service.xxx> <message-text>");
            return;
        }
        
        InputStream is = null;
        
        try {
            is = new FileInputStream(args[0]);
            
            Properties props = new Properties();
            props.load(is);
            
            JID jid         = new JID(props.getProperty("auth.jid"));
            String pwd      = props.getProperty("auth.password");
            String host     = props.getProperty("auth.host");
            String port     = props.getProperty("auth.port");
            String secur    = props.getProperty("auth.ssl_tls");
            String anon     = props.getProperty("authenticated.anon");
            boolean secure  = Boolean.valueOf(secur);
            
            ConnectionConfiguration cc    = XmppUtils.getConnectionCfg(jid, 
                                                                       host, 
                                                                       port, 
                                                                       secure);
            final PJBService pjbs = new PJBService(cc, 
                                                   new EchoXmppHandler(), 
                                                   Collections.EMPTY_LIST, 
                                                   false);
            
            pjbs.start(jid, pwd);
            pjbs.sendMessage(new JID(args[1]), args[2], null, true);
            pjbs.stop();
            
        } catch(IOException e) {
            System.err.println("Error: "+ e.getMessage());
        } finally {
            XmppUtils.closeQuietly(is);
        }
        
    }
}
