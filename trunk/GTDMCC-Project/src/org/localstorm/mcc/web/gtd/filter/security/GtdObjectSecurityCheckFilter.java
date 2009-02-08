package org.localstorm.mcc.web.gtd.filter.security;

import javax.servlet.http.HttpServletResponse;
import org.localstorm.mcc.web.filter.SecurityCheckFilter;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import org.localstorm.mcc.ejb.users.User;

/**
 *
 * @author Alexey Kuznetsov
 */
public class GtdObjectSecurityCheckFilter extends SecurityCheckFilter
{
    
    public GtdObjectSecurityCheckFilter() {
    
    }

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, User user)
            throws IOException,
                   ServletException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig arg) throws ServletException {
        
    }
}