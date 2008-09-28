package org.localstorm.punjab;

import java.io.IOException;
import java.io.InputStream;
import javax.net.ssl.SSLSocketFactory;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;

/**
 *
 * @author Alexey Kuznetsov
 */
public class XmppUtils 
{
    public static boolean sendSilently(String message, Chat chat)
    {
        if (message!=null && message.length()>0) {
            try {
                chat.sendMessage(message);
                return true;
            } catch(Throwable e) {
                return false;
            }
        } else {
            return false;
        }
    }
    
    public static ConnectionConfiguration getConnectionCfg(JID jid,
                                                           String host,
                                                           String port,
                                                           boolean secure) 
    {
        int portVal = new Integer(port);
        ConnectionConfiguration cc = null;
        
        if (jid.getService().equals(host))
        {
            cc = new ConnectionConfiguration(host, portVal);
        } else {
            cc = new ConnectionConfiguration(host, portVal, jid.getService());
        }
        
        cc.setReconnectionAllowed(true);
        cc.setCompressionEnabled(true);
        cc.setSASLAuthenticationEnabled(true);

        if (secure) {
            cc.setSecurityMode(ConnectionConfiguration.SecurityMode.enabled);
            SSLSocketFactory sockFact = (SSLSocketFactory) SSLSocketFactory.getDefault();
            cc.setSocketFactory(sockFact);
        }
        
        return cc;
    }
    
    public static boolean closeQuietly(InputStream is) {
        if (is!=null) {
            try {
                is.close();
                return true;
            } catch (IOException e){
                return false;
            }
        }
        return true;
    }

}
