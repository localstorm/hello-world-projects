package org.localstorm.mcc.web.cashflow.servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;
import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.cashflow.Asset;
import org.localstorm.mcc.ejb.cashflow.AssetManager;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.Constants;
import org.localstorm.mcc.web.SessionKeys;
import org.localstorm.mcc.web.cashflow.actions.RoundUtil;
import org.localstorm.mcc.web.cashflow.actions.wrap.AssetWrapper;
import org.localstorm.mcc.web.cashflow.actions.wrap.WrapUtil;
import org.localstorm.mcc.web.util.SessionUtil;

/**
 * @author localstorm
 */
public class TotalWealthPieChartServlet extends HttpServlet
{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sess = req.getSession(true);
        User user = (User) SessionUtil.getValue(sess, SessionKeys.USER);

        if (user==null)
        {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);

        String curDate = sdf.format(new Date());
        final JFreeChart chart = ChartFactory.createPieChart3D(
            "Total wealth chart ("+curDate+")",  // chart title
            this.getWealthDataset(user),         // data
            true,                                // include legend
            true,
            false
        );

        final PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        plot.setNoDataMessage("No data to display");
        
        resp.setContentType("image/png");
        ChartUtilities.writeChartAsPNG(resp.getOutputStream(), chart, 640, 480);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    private PieDataset getWealthDataset(User user) {

        AssetManager am = ContextLookup.lookup(AssetManager.class, AssetManager.BEAN_NAME);
        Collection<Asset> assets = am.findAssetsByOwner(user);
        assets = WrapUtil.wrapAssets(assets, am);

        MathContext rounding = new MathContext(0);
        final DefaultPieDataset result = new DefaultPieDataset();

        for (Asset a: assets)
        {
            AssetWrapper aw = (AssetWrapper) a;
            BigDecimal nw = aw.getNetWealth();

            BigDecimal hundred = new BigDecimal(100);
            
            BigDecimal nwRounded = nw.multiply(hundred);
            nwRounded = (new BigDecimal(nwRounded.longValue())).divide(hundred);

            result.setValue(aw.getName()+"="+nwRounded.toPlainString(), nw);
        }

        return result;

    }

}
