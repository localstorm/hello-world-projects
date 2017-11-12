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
public class PeoplePersonSecurityCheckFilter extends SecurityCheckFilter
{
    public static final String PERSON_ID_PARAM = "personId";

    @Override
    @SuppressWarnings("unchecked")
    public void doFilter(HttpServletRequest req, HttpServletResponse res, User user)
            throws IOException,
                   ServletException
    {
        String pid = req.getParameter(PERSON_ID_PARAM);

        if (pid!=null)
        {
            Integer personId = Integer.parseInt(pid);
            SecurityUtil.checkPersonSecurity(req.getSession(true), personId, user, log);
        }
    }

}