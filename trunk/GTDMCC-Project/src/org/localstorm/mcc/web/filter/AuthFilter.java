package org.localstorm.mcc.web.filter;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.except.ObjectNotFoundException;
import org.localstorm.mcc.ejb.users.*;
import org.localstorm.mcc.web.SessionKeys;
import org.localstorm.mcc.web.util.SessionUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
public class AuthFilter implements Filter 
{
    
    
    
    public AuthFilter() {
    
    }

    @Override
    public void doFilter(ServletRequest _req, ServletResponse _res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) _req;
        HttpSession sess = req.getSession();
            
        if (SessionUtil.isEmpty(sess, SessionKeys.USER)) {
            req.getRequestDispatcher("/jsp/login.jsp").forward(_req, _res);
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