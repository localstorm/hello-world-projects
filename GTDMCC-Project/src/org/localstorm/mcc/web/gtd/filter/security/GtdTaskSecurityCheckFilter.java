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
public class GtdTaskSecurityCheckFilter extends SecurityCheckFilter
{
    private static final String TASK_ID_PARAM = "taskId";

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, User user)
            throws IOException,
                   ServletException
    {
        String tid = req.getParameter(TASK_ID_PARAM);

        if (tid!=null)
        {
            Integer taskId = Integer.parseInt(tid);
            SecurityUtil.checkTaskSecurity(req.getSession(true), taskId, user, log);
        }
    }


}