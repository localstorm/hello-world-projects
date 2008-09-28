package org.localstorm.punjab;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Properties;
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
            
            ConnectionConfiguration cc    = XmppUtils.getConnectionCfg(jid, 
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
        } finally {
            XmppUtils.closeQuietly(is);
        }
        
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
