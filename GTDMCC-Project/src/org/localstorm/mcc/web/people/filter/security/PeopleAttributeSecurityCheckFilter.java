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
public class PeopleAttributeSecurityCheckFilter extends SecurityCheckFilter
{
    public static final String ATTRIBUTE_ID_PARAM = "attributeId";

    @Override
    @SuppressWarnings("unchecked")
    public void doFilter(HttpServletRequest req, HttpServletResponse res, User user)
            throws IOException,
                   ServletException
    {
        String aid = req.getParameter(ATTRIBUTE_ID_PARAM);

        if (aid!=null)
        {
            Integer attributeId = Integer.parseInt(aid);
            SecurityUtil.checkAttributeSecurity(req.getSession(true), attributeId, user, log);
        }
    }

}