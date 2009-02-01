package org.localstorm.mcc.web.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
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
    public void doFilter(ServletRequest _req, ServletResponse _res, FilterChain chain) throws IOException, ServletException {
        User user  = super.getUser((HttpServletRequest) _req);
            
        if (user==null) {
            _req.getRequestDispatcher(Views.LOGIN).forward(_req, _res);
            return;
        }
        
        chain.doFilter(_req, _res);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    
    }
    

}