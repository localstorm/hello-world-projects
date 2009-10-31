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
import org.localstorm.mcc.web.cashflow.CashflowSessionKeys;
import org.localstorm.mcc.web.cashflow.charting.NetWealthHistoryChartGenerator;
import org.localstorm.mcc.web.util.SessionUtil;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @author localstorm
 */
public class NetWealthHistoryChartServlet extends HttpServlet
{
    private static final long serialVersionUID = -5239734711496278134L;

    @Override
    @Logged
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sess = req.getSession(true);
        User user = (User) SessionUtil.getValue(sess, CashflowSessionKeys.USER);

        if (user==null) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String sstgt = req.getParameter("showTargets");
        boolean showTgts = false;
        if (sstgt!=null) {
            showTgts = Boolean.parseBoolean(sstgt);
        }

        String sdbt = req.getParameter("includeDebt");
        boolean includeDebt = false;
        if (sdbt!=null) {
            includeDebt = Boolean.parseBoolean(sdbt);
        }

        String name = (showTgts) ? "Targeted net wealth" : "Net wealth";
        name        += (includeDebt) ? " history" : " w/o debt";
        JFreeChart chart = NetWealthHistoryChartGenerator.getChart(user, null, name, showTgts, includeDebt);

        resp.setContentType(Constants.PNG_CONTENT_TYPE);
        ChartUtilities.writeChartAsPNG(resp.getOutputStream(), chart, 640, 480);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
   
}
