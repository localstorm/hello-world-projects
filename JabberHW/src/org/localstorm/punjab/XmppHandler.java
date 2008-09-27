package org.localstorm.punjab;

/**
 *
 * @author Alexey Kuznetsov
 */
public interface XmppHandler 
{
    public String onMessage(String msg, String from);
    public boolean isAllowed(String from);
    public String getDenialMessage();
}
