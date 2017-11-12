package org.localstorm.mcc.web.cashflow.filter.security;

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
public class CashflowTargetSecurityCheckFilter extends SecurityCheckFilter
{
    public static final String TARGET_ID_PARAM = "targetId";

    @Override
    @SuppressWarnings("unchecked")
    public void doFilter(HttpServletRequest req, HttpServletResponse res, User user)
            throws IOException,
                   ServletException
    {
        String tid = req.getParameter(TARGET_ID_PARAM);

        if (tid!=null)
        {
            Integer targetId = Integer.parseInt(tid);
            SecurityUtil.checkTargetSecurity(req.getSession(true), targetId, user, log);
        }
    }

}