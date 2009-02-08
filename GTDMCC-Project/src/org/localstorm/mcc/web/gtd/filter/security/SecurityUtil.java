package org.localstorm.mcc.web.gtd.filter.security;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
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
    public static final String CONTEXT_ID_PARAM = "contextId";
    
    public static void checkContextSecurity(HttpServletRequest req, User user, Logger log)
            throws SecurityRuntimeException,
                   NumberFormatException
    {
        String cid = req.getParameter(CONTEXT_ID_PARAM);
        if (cid != null) {
            Integer contextId = Integer.parseInt(cid);
            log.info("Checking access to context=" + cid + " for user=" + user.getLogin());
            Map<Integer, Boolean> acm = (Map<Integer, Boolean>) SessionUtil.getValue(req.getSession(true),
                                                                                     SessionKeys.ACCESSIBLE_CONTEXTS_MAP);
            if (acm.containsKey(contextId)) {
                return;
            }

            throw new SecurityRuntimeException("Access denied!");
        }
    }

    public static void checkFileSecurity(HttpServletRequest req, User user, Logger log)
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static void checkListSecurity(HttpServletRequest req, User user, Logger log)
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static void checkObjectSecurity(HttpServletRequest req, User user, Logger log)
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static void checkTaskSecurity(HttpServletRequest req, User user, Logger log)
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
