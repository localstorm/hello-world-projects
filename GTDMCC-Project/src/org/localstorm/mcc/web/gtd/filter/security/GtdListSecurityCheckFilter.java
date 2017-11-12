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
public class GtdListSecurityCheckFilter extends SecurityCheckFilter
{
    private static final String LIST_ID_PARAM = "listId";
 
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, User user)
            throws IOException,
                   ServletException
    {
        String lid = req.getParameter(LIST_ID_PARAM);

        if (lid!=null)
        {
            Integer listId = Integer.parseInt(lid);
            SecurityUtil.checkListSecurity(req.getSession(true), listId, user, log);
        }
    }


}