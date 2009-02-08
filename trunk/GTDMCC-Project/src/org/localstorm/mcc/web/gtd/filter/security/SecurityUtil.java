package org.localstorm.mcc.web.gtd.filter.security;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.gtd.lists.GTDList;
import org.localstorm.mcc.ejb.gtd.lists.ListManager;
import org.localstorm.mcc.ejb.gtd.tasks.Task;
import org.localstorm.mcc.ejb.gtd.tasks.TaskManager;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.SecurityRuntimeException;
import org.localstorm.mcc.web.SessionKeys;
import org.localstorm.mcc.web.util.SessionUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
class SecurityUtil 
{
    public static final String ACCESS_DENIED = "Access denied!";
    
    public static void checkContextSecurity(HttpSession sess, Integer contextId, User user, Logger log)
            throws SecurityRuntimeException,
                   NumberFormatException
    {
        if (contextId!=null)
        {
            log.info("Checking access to context=" + contextId + " for user=" + user.getLogin());
            Map<Integer, Boolean> acm = (Map<Integer, Boolean>) SessionUtil.getValue(sess,
                                                                                     SessionKeys.ACCESSIBLE_CONTEXTS_MAP);
            if (acm.containsKey(contextId)) {
                return;
            }

            throw new SecurityRuntimeException(ACCESS_DENIED);
        }
    }

    public static void checkFileSecurity(HttpServletRequest req, User user, Logger log)
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static void checkListSecurity(HttpSession sess, Integer listId, User user, Logger log)
            throws SecurityRuntimeException,
                   NumberFormatException
    {
        if (listId!=null)
        {
            log.info("Checking access to list=" + listId + " for user=" + user.getLogin());
            Integer contextId;

            try {

                ListManager lm = ContextLookup.lookup(ListManager.class, ListManager.BEAN_NAME);
                GTDList list = lm.findById(listId);
                contextId = list.getContext().getId();
                
            } catch(Exception e) {
                log.error(e);
                throw new SecurityRuntimeException(ACCESS_DENIED);
            }

            checkContextSecurity(sess, contextId, user, log);
        }
    }

    public static void checkObjectSecurity(HttpServletRequest req, User user, Logger log)
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static void checkTaskSecurity(HttpSession sess, Integer taskId, User user, Logger log)
            throws SecurityRuntimeException,
                   NumberFormatException
    {
        if (taskId!=null)
        {
            log.info("Checking access to taskt=" + taskId + " for user=" + user.getLogin());
            Integer listId;

            try {

                TaskManager tm = ContextLookup.lookup(TaskManager.class, TaskManager.BEAN_NAME);
                Task      task = tm.findById(taskId);
                listId = task.getList().getId();

            } catch(Exception e) {
                log.error(e);
                throw new SecurityRuntimeException(ACCESS_DENIED);
            }

            checkListSecurity(sess, listId, user, log);
        }
    }

}
