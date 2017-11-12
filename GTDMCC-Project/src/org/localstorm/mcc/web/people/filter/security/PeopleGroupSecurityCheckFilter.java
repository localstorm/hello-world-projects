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
public class PeopleGroupSecurityCheckFilter extends SecurityCheckFilter
{
    public static final String GROUP_ID_PARAM = "groupId";

    @Override
    @SuppressWarnings("unchecked")
    public void doFilter(HttpServletRequest req, HttpServletResponse res, User user)
            throws IOException,
                   ServletException
    {
        String gid = req.getParameter(GROUP_ID_PARAM);

        if (gid!=null)
        {
            Integer groupId = Integer.parseInt(gid);
            SecurityUtil.checkGroupSecurity(req.getSession(true), groupId, user, log);
        }
    }

}