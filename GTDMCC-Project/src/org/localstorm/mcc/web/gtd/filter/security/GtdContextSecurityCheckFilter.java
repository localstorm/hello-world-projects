package org.localstorm.mcc.web.gtd.filter.security;

import org.localstorm.mcc.web.SecurityRuntimeException;
import javax.servlet.http.HttpServletResponse;
import org.localstorm.mcc.web.filter.SecurityCheckFilter;
import java.io.IOException;
import java.util.Collection;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import org.localstorm.mcc.ejb.gtd.contexts.Context;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.SessionKeys;
import org.localstorm.mcc.web.util.SessionUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
public class GtdContextSecurityCheckFilter extends SecurityCheckFilter
{
    public static final String CONTEXT_ID_PARAM = "contextId";

    public GtdContextSecurityCheckFilter() {
    
    }

    @Override
    @SuppressWarnings("unchecked")
    public void doFilter(HttpServletRequest req, HttpServletResponse res, User user)
            throws IOException,
                   ServletException
    {
        String cid = req.getParameter(CONTEXT_ID_PARAM);

        if (cid!=null) {
            Integer contextId = Integer.parseInt(cid);

            log.info("Checking access to context="+cid+" for user="+user.getLogin());

            Collection<Context> ctxs = (Collection<Context>) SessionUtil.getValue(req.getSession(true), SessionKeys.CONTEXTS);
            for ( Context c: ctxs ) {
                if ( c.getId().equals(contextId) ) {
                    return;
                }
            }

            log.info("Access denied!");

            throw new SecurityRuntimeException();
        }
    }


    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        
    }
}