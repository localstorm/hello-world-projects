package org.localstorm.punjab;

/**
 *
 * @author Alexey Kuznetsov
 */
public class EchoXmppHandler implements XmppHandler
{
    public String onMessage(String msg, String fromJid) {
        System.out.println("Message from "+fromJid+": "+msg);
        return msg;
    }

    public boolean isAllowed(String from) {
        return true;
    }

    public String getDenialMessage() {
        return "You're not authorized for this service!";
    }
    
}
