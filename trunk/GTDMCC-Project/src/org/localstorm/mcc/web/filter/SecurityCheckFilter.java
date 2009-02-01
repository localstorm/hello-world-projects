package org.localstorm.mcc.web.filter;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.SessionKeys;

/**
 *
 * @author Alexey Kuznetsov
 */
public abstract class SecurityCheckFilter implements Filter
{
    public User getUser(HttpServletRequest req)
    {
        HttpSession sess = req.getSession(true);
        return (User) sess.getAttribute(SessionKeys.USER);
    }
}
