package org.localstorm.springapp.servlet;

import org.localstorm.springapp.beans.RequestScopedBean;
import org.localstorm.springapp.beans.SingletonBean;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: localstorm
 * Date: Nov 14, 2009
 * Time: 11:57:46 AM
 */
public class TestServlet extends HttpServlet {
    private static final long serialVersionUID = 6657953530375962186L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        RequestScopedBean o = (RequestScopedBean) wac.getBean("xBean");
        o = (RequestScopedBean) wac.getBean("xBean");
        o = (RequestScopedBean) wac.getBean("xBean");

        SingletonBean s = (SingletonBean) wac.getBean("yBean");
        
        resp.getOutputStream().println("<b>"+o.getMessage()+":"+s.getMessage()+"</b>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
