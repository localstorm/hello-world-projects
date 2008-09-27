package org.localstorm.punjab;

import java.util.Collection;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

/**
 *
 * @author Alexey Kuznetsov
 */
public class PJBService 
{
    private XmppHandler handler;
    private ConnectionConfiguration config;
    private XMPPConnection conn;
    private boolean anonAllowed;
    
    public PJBService(ConnectionConfiguration cc, 
                      XmppHandler handler, 
                      Collection<JID> authenticatedJids, 
                      boolean anonymous) 
    {
        if (anonymous) {
            this.handler   = handler;
        } else {
            this.handler   = new SecurityXmppFilter(handler, authenticatedJids);
        }
        this.config        = cc;
        this.anonAllowed   = anonymous;
    }
    
    public void start(JID jid, String password) throws XMPPException
    {
        this.conn = new XMPPConnection(this.config);
        this.conn.connect();
        
        this.conn.login(jid.toString(), password);

        System.out.println("Logged in as "+jid.toString());
        
        Presence presence = new Presence(Presence.Type.available);
        this.conn.sendPacket(presence);

        this.conn.getChatManager().addChatListener(new ChatManagerListener() {

            public void chatCreated(Chat chat, boolean locally) {

                String peer  = chat.getParticipant();

                if (!PJBService.this.handler.isAllowed(peer))
                {
                    String reply = PJBService.this.handler.getDenialMessage();
                    XmppUtils.sendSilently(reply, chat);

                } else {

                    MessageListener ml = new MessageListener() {

                        public void processMessage(Chat chat, Message message) {
                            try {

                                String peer  = chat.getParticipant();
                                String reply = PJBService.this.handler.onMessage(message.getBody(), 
                                                                                 peer);
                                XmppUtils.sendSilently(reply, chat);

                            } catch(Exception e) {
                                System.err.println("Error: "+e.getMessage());
                            }
                            
                            chat.removeMessageListener(this);
                        }

                    };
                    
                    chat.addMessageListener(ml);
                }

            }
        });

        
    }
    
    public void join() throws InterruptedException
    {
        while (this.conn.isConnected())
        {
            Thread.sleep(1000);
        }
    }
    
    public void stop()
    {
        System.out.println("Stopping...");
        Presence presence = new Presence(Presence.Type.unsubscribed);
        this.conn.disconnect(presence);
    }
}
