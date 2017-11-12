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
public class GtdInboxSecurityCheckFilter extends SecurityCheckFilter
{
    public static final String ENTRY_ID_PARAM = "entryId";

    @Override
    @SuppressWarnings("unchecked")
    public void doFilter(HttpServletRequest req, HttpServletResponse res, User user)
            throws IOException,
                   ServletException
    {
        String ieId = req.getParameter(ENTRY_ID_PARAM);

        if (ieId!=null)
        {
            Integer entryId = Integer.parseInt(ieId);
            SecurityUtil.checkInboxEntrySecurity(req.getSession(true), entryId, user, log);
        }
    }

}