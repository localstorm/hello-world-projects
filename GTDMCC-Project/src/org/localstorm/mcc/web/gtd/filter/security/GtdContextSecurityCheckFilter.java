package org.localstorm.mcc.web.gtd.filter.security;

import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.filter.SecurityCheckFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author Alexey Kuznetsov
 */
public class GtdContextSecurityCheckFilter extends SecurityCheckFilter
{
    public static final String CONTEXT_ID_PARAM = "contextId";

    @Override
    @SuppressWarnings("unchecked")
    public void doFilter(HttpServletRequest req, HttpServletResponse res, User user)
            throws IOException,
                   ServletException
    {
        String cid = req.getParameter(CONTEXT_ID_PARAM);

        if (cid!=null)
        {
            Integer contextId = Integer.parseInt(cid);
            SecurityUtil.checkContextSecurity(req.getSession(true), contextId, user, log);
        }
    }

}