package org.localstorm.mcc.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author Alexey Kuznetsov
 */
public class RestrictionFilter implements Filter 
{

    public RestrictionFilter() {
    }

    @Override
    public void doFilter(ServletRequest _req, ServletResponse _res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) _res;
        res.sendError( HttpServletResponse.SC_FORBIDDEN );
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    
    }
    

}
