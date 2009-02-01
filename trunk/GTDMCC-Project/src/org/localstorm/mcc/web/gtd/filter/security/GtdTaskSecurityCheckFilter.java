package org.localstorm.mcc.web.gtd.filter.security;

import org.localstorm.mcc.web.filter.SecurityCheckFilter;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import org.localstorm.mcc.ejb.users.User;

/**
 *
 * @author Alexey Kuznetsov
 */
public class GtdTaskSecurityCheckFilter extends SecurityCheckFilter
{
    
    public GtdTaskSecurityCheckFilter() {
    
    }

    @Override
    public void doFilter(ServletRequest _req, 
                         ServletResponse _res, 
                         FilterChain chain) throws IOException, ServletException 
    {
        HttpServletRequest req = (HttpServletRequest) _req;
        User user = super.getUser(req);

        //SessionUtil.isEmpty(sess, SessionKeys.CONTEXTS)

        chain.doFilter(_req, _res);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig arg) throws ServletException {
        
    }
}