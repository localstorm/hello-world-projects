package org.localstorm.punjab;

import java.util.Collection;

/**
 *
 * @author Alexey Kuznetsov
 */
public class SecurityXmppFilter implements XmppHandler 
{
    private XmppHandler handler;
    private Collection<JID> authorized;
    
    public SecurityXmppFilter(XmppHandler handler, Collection<JID> auth) {
        this.handler    = handler;
        this.authorized = auth;
    }

    public String getDenialMessage() {
        return this.handler.getDenialMessage();
    }

    public boolean isAllowed(String from) {
        
        if (from==null || from.length()==0)
        {
            return false;
        }
        
        from = from.toLowerCase();
        
        for (JID jid: authorized)
        {
            String jidString = jid.toString().toLowerCase();
            if (from.startsWith(jidString))
            {
                return true;
            }
        }
        
        return false;
    }

    public String onMessage(String msg, String from) {
        return this.handler.onMessage(msg, from);
    }
}
