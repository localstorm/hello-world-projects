package org.localstorm.springapp.servlet;

import org.localstorm.springapp.beans.RequestScopedBean;
import org.localstorm.springapp.beans.SingletonBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;

/**
 * User: localstorm
 * Date: Nov 14, 2009
 * Time: 11:57:46 AM
 */
public class TestServlet extends HttpServlet {
    private static final long serialVersionUID = 6657953530375962186L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
            RequestScopedBean o = (RequestScopedBean) wac.getBean("xBean");
            o = (RequestScopedBean) wac.getBean("xBean");
            o = (RequestScopedBean) wac.getBean("xBean");

            SingletonBean s = (SingletonBean) wac.getBean("yBean");

            DataSource ds = (DataSource) wac.getBean("jndiDataSource");
            JdbcTemplate jdbcT = new JdbcTemplate(ds);

            long count = jdbcT.queryForLong("select count(*) from TEST");
            resp.getOutputStream().println("<b>" + o.getUser().getName() + ":" + s.getMessage() + ":" + o.getSome().getName() + ", count=" + count+"</b>");
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
