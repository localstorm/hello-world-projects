package org.localstorm.mcc.web.gtd.filter.security;

import javax.servlet.http.HttpServletResponse;
import org.localstorm.mcc.web.filter.SecurityCheckFilter;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import org.localstorm.mcc.ejb.users.User;

/**
 *
 * @author Alexey Kuznetsov
 */
public class GtdTaskSecurityCheckFilter extends SecurityCheckFilter
{

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, User user)
            throws IOException,
                   ServletException
    {
        SecurityUtil.checkTaskSecurity(req, user, log);
    }


}