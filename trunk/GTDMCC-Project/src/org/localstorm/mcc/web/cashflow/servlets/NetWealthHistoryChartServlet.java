package org.localstorm.mcc.web.cashflow.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.Constants;
import org.localstorm.mcc.web.SessionKeys;
import org.localstorm.mcc.web.cashflow.charting.NetWealthHistoryChartGenerator;
import org.localstorm.mcc.web.util.SessionUtil;

/**
 * @author localstorm
 */
public class NetWealthHistoryChartServlet extends HttpServlet
{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sess = req.getSession(true);
        User user = (User) SessionUtil.getValue(sess, SessionKeys.USER);

        if (user==null) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String sstgt = req.getParameter("showTargets");
        boolean showTgts = false;
        if (sstgt!=null) {
            showTgts = Boolean.parseBoolean(sstgt);
        }

        String name = (showTgts) ? "Net wealth history (with targets)" : "Net wealth history";
        JFreeChart chart = NetWealthHistoryChartGenerator.getChart(user, null, name, showTgts);

        resp.setContentType(Constants.PNG_CONTENT_TYPE);
        ChartUtilities.writeChartAsPNG(resp.getOutputStream(), chart, 640, 480);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
   
}