package org.localstorm.mcc.web.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 *
 * @author Alexey Kuznetsov
 */
public class RequestLogFilter implements Filter
{
    private static final Logger log = Logger.getLogger(RequestLogFilter.class);
    
    public RequestLogFilter() {
    
    }

    @Override
    public void doFilter(ServletRequest _req, ServletResponse _res, FilterChain chain) throws IOException, ServletException {
        log.info("Request URI: "+((HttpServletRequest)_req).getRequestURI());
        
        chain.doFilter(_req, _res);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    
    }
    

}