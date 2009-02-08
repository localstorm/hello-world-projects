package org.localstorm.mcc.web.filter;

import java.io.IOException;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.Views;

/**
 *
 * @author Alexey Kuznetsov
 */
public class AuthFilter extends SecurityCheckFilter
{
    
    
    
    public AuthFilter() {
    
    }

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, User user)
            throws IOException,
                   ServletException
    {
        if (user==null) {
            req.getRequestDispatcher(Views.LOGIN).forward(req, res);
            return;
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    
    }
    

}