package org.localstorm.mcc.web.starter;

import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.apache.log4j.Logger;
import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.gtd.InboxManager;
import org.localstorm.mcc.ejb.gtd.entity.InboxEntry;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.ejb.users.UserManager;
import org.localstorm.mcc.xmpp.JID;
import org.localstorm.mcc.xmpp.XmppHandler;

/**
 *
 * @author Alexey Kuznetsov
 */
public class AssistantXmppHandler implements XmppHandler
{
    private static final Logger log = Logger.getLogger(AssistantXmppHandler.class);

    @Override
    public String handle(int uid, JID from, JID to, String message)
    {
        UserTransaction ut = null;

        try
        {
            try {
                InboxManager im = ContextLookup.lookup(InboxManager.class, InboxManager.BEAN_NAME);
                UserManager  um = ContextLookup.lookup(UserManager.class,  UserManager.BEAN_NAME);

                ut = ContextLookup.lookupTransaction();
                ut.begin();

                User user = um.findById(uid);
                InboxEntry note = new InboxEntry(message, user);
                im.submitNote(note);

                ut.commit();
                return "accepted.";

            } catch (Exception e) {
                log.error("Transaction failed:", e);
                if (ut!=null && Status.STATUS_COMMITTED!=ut.getStatus() &&
                    ut.getStatus()!=Status.STATUS_NO_TRANSACTION) {
                    ut.setRollbackOnly();
                }

            } finally {
                if (ut!=null &&
                    Status.STATUS_COMMITTED!=ut.getStatus() &&
                    ut.getStatus()!=Status.STATUS_NO_TRANSACTION)
                {
                    ut.rollback();
                }
            }
        } catch(SystemException e) {
            log.fatal(e.getMessage(), e);
        }
        
        return "Server error. Contact service administrator please.";
    }

}
