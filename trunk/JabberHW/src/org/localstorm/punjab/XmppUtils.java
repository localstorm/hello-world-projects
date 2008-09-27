package org.localstorm.punjab;

import org.jivesoftware.smack.Chat;

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
}
