package org.localstorm.punjab;

/**
 *
 * @author Alexey Kuznetsov
 */
public class EchoXmppHandler implements XmppHandler
{
    public String onMessage(String msg, String from) {
        System.out.println("Message from "+from+": "+msg);
        return msg;
    }

    public boolean isAllowed(String from) {
        return true;
    }

    public String getDenialMessage() {
        return "You're not authorized for this service!";
    }
    
}
