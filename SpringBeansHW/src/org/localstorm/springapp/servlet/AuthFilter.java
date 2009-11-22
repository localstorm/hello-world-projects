package org.localstorm.springapp.servlet;

import org.localstorm.springapp.beans.RequestScopedBean;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: localstorm
 * Date: Nov 17, 2009
 * Time: 10:42:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class AuthFilter implements Filter {

    private ServletContext ctx;

    public void init(FilterConfig filterConfig) throws ServletException {
        ctx = filterConfig.getServletContext();
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(ctx);
        RequestScopedBean o = (RequestScopedBean) wac.getBean("xBean");
        o.getUser().setName("Nigger");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {

    }
}
