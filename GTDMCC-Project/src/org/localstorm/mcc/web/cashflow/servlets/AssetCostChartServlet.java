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
import org.localstorm.mcc.web.cashflow.charting.AssetCostHistoryChartGenerator;
import org.localstorm.mcc.web.util.SessionUtil;

/**
 * @author localstorm
 */
public class AssetCostChartServlet extends HttpServlet
{
    public static final String ASSERT_ID_PARAMETER = "assetId";
    public static final String PERIOD              = "period";
    public static final String NAME                = "name";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sess = req.getSession(true);
        User user        = (User) SessionUtil.getValue(sess, SessionKeys.USER);

        String said      = req.getParameter(ASSERT_ID_PARAMETER);
        String period    = req.getParameter(PERIOD);
        String name      = req.getParameter(NAME);
        
        Integer daysOffset = null;

        if (period!=null && period.length()>0) {
            daysOffset = Integer.parseInt(period);
        }

        if (user==null || said==null) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        Integer assetId  = new Integer(said);
        JFreeChart chart = AssetCostHistoryChartGenerator.getChart(user, assetId, daysOffset, name);

        if ( chart==null ) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        resp.setContentType(Constants.PNG_CONTENT_TYPE);
        ChartUtilities.writeChartAsPNG(resp.getOutputStream(), chart, 640, 480);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    
}