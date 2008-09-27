package org.localstorm.punjab;

import java.io.FileInputStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Properties;
import javax.net.ssl.SSLSocketFactory;
import org.jivesoftware.smack.ConnectionConfiguration;

/**
 *
 * @author Alexey Kuznetsov
 */
public class Punjab 
{
    public static void main(String[] args) 
    {
        if (args.length!=1)
        {
            System.err.println("Usage: <config_file>");
            return;
        }
        
        try {

            Properties props = new Properties();
            props.load(new FileInputStream(args[0]));
            
            JID jid         = new JID(props.getProperty("auth.jid"));
            String pwd      = props.getProperty("auth.password");
            String host     = props.getProperty("auth.host");
            String port     = props.getProperty("auth.port");
            String secur    = props.getProperty("auth.ssl_tls");
            String clist    = props.getProperty("authenticated.jids");
            String anon     = props.getProperty("authenticated.anon");
            String handler  = props.getProperty("handler.class");
            
            boolean isAnonAlowed = Boolean.valueOf(anon);
            boolean secure       = Boolean.valueOf(secur);
            
            Class handlerClass   = Class.forName(handler);
            
            Object o = handlerClass.newInstance();
            if (!(o instanceof XmppHandler))
            {
                throw new ClassCastException("XmppHandler subclass expected!");
            }
            
            ConnectionConfiguration cc    = Punjab.getConnectionCfg(jid, 
                                                                    host, 
                                                                    port, 
                                                                    secure);
            XmppHandler             xmpph = (XmppHandler) o;
            
            final PJBService pjbs = new PJBService(cc, 
                                                   xmpph, 
                                                   Punjab.getApprovedPeers(clist), 
                                                   isAnonAlowed);
            
            pjbs.start(jid, pwd);

            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    pjbs.stop();
                }
            });
            
            pjbs.join();
            
        } catch(Exception e) {
            System.err.println("Error: " + e.getMessage());
            return;
        }
        
    }

    private static ConnectionConfiguration getConnectionCfg(JID jid,
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
    
    private static Collection<JID> getApprovedPeers(String list)
    {
        Collection<JID> result = new LinkedList<JID>();
        if (list!=null && list.length()>0)
        {
            String[] jids = list.split(",");
            
            for (String jid: jids) {
                result.add(new JID(jid.trim()));
            }
        }
        
        return result;
    }
}
