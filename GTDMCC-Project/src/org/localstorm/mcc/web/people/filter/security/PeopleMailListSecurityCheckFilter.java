package org.localstorm.mcc.web.people.filter.security;

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
public class PeopleMailListSecurityCheckFilter extends SecurityCheckFilter
{
    public static final String MAIL_LIST_ID_PARAM = "mailListId";

    @Override
    @SuppressWarnings("unchecked")
    public void doFilter(HttpServletRequest req, HttpServletResponse res, User user)
            throws IOException,
                   ServletException
    {
        String mlid = req.getParameter(MAIL_LIST_ID_PARAM);

        if (mlid!=null)
        {
            Integer mailListId = Integer.parseInt(mlid);
            SecurityUtil.checkMailListSecurity(req.getSession(true), mailListId, user, log);
        }
    }

}