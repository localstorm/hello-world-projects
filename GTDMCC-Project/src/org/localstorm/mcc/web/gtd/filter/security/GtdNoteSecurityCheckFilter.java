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
public class GtdNoteSecurityCheckFilter extends SecurityCheckFilter
{
    private static final String NOTE_ID_PARAM = "noteId";

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, User user)
            throws IOException,
                   ServletException
    {
        String nid = req.getParameter(NOTE_ID_PARAM);

        if (nid!=null)
        {
            Integer noteId = Integer.parseInt(nid);
            SecurityUtil.checkNoteSecurity(req.getSession(true), noteId, user, log);
        }
    }

}