package org.localstorm.mcc.web.cashflow.servlets;

import java.io.IOException;
import java.math.BigDecimal;
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
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.cashflow.asset.Asset;
import org.localstorm.mcc.ejb.cashflow.asset.AssetManager;
import org.localstorm.mcc.ejb.cashflow.asset.Cost;
import org.localstorm.mcc.ejb.cashflow.asset.ValuableObject;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.Constants;
import org.localstorm.mcc.web.SessionKeys;
import org.localstorm.mcc.web.util.SessionUtil;

/**
 * @author localstorm
 */
public class AssetCostChartServlet extends HttpServlet
{
    public static final String ASSERT_ID_PARAMETER = "assetId";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sess = req.getSession(true);
        User user        = (User) SessionUtil.getValue(sess, SessionKeys.USER);
        String said      = req.getParameter(ASSERT_ID_PARAMETER);

        if (user==null || said==null) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        Integer assetId = new Integer(said);
        XYDataset dataset = this.getAssetCostDataset(user, assetId);

        if( dataset==null ) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
        String curDate = sdf.format(new Date());

        JFreeChart chart = ChartFactory.createTimeSeriesChart("Asset cost history (until "+curDate+")",
                                                              "Time line",
                                                              "Costs",
                                                              dataset,
                                                              true,
                                                              true,
                                                              false);

        XYPlot plot = (XYPlot) chart.getPlot();

        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat(Constants.REDUCED_DATE_FORMAT));

        plot.setForegroundAlpha(0.7f);
        plot.setNoDataMessage("No data to display");

        resp.setContentType("image/png");
        ChartUtilities.writeChartAsPNG(resp.getOutputStream(), chart, 640, 480);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    private XYDataset getAssetCostDataset(User user, Integer assetId) {

        
        AssetManager am = ContextLookup.lookup(AssetManager.class, AssetManager.BEAN_NAME);
        Asset asset = am.findAssetById(assetId);
        ValuableObject vo = asset.getValuable();

        if (!user.getId().equals(vo.getOwner().getId())) {
            return null;
        }

        Collection<Cost> costs = am.getCostHistory(vo);

        TimeSeries sell     = new TimeSeries(asset.getName()+" sell cost");
        TimeSeries buy      = new TimeSeries(asset.getName()+" buy cost");
        TimeSeries buyFx    = new TimeSeries(asset.getName()+" exchange buy cost");
        TimeSeries sellFx   = new TimeSeries(asset.getName()+" exchange sell cost");

        boolean buyFxEnable = false;
        boolean sellFxEnable = false;

        TimeSeriesCollection tsc = new TimeSeriesCollection();

        Cost current = am.getCurrentCost(vo);

        for (Cost c: costs) {
            sell.addOrUpdate(new Day(c.getActuationDate()), c.getSell());
            buy.addOrUpdate(new Day(c.getActuationDate()), c.getBuy());

            buyFxEnable  |= (c.getExchangeBuy() != null);
            sellFxEnable |= (c.getExchangeSell() != null);
        }

        buy.addOrUpdate(new Day(new Date()), current.getBuy());
        sell.addOrUpdate(new Day(new Date()), current.getSell());

        tsc.addSeries(buy);
        tsc.addSeries(sell);

        if (buyFxEnable) {
            for (Cost c: costs) {
                buyFx.addOrUpdate(new Day(c.getActuationDate()), this.nvlCost(c.getExchangeBuy(), c.getBuy()));
            }
            buyFx.addOrUpdate(new Day(new Date()), this.nvlCost(current.getExchangeBuy(), current.getBuy()));
            tsc.addSeries(buyFx);
        }

        if (sellFxEnable) {
            for (Cost c: costs) {
                sellFx.addOrUpdate(new Day(c.getActuationDate()), this.nvlCost(c.getExchangeSell(), c.getSell()));
            }
            sellFx.addOrUpdate(new Day(new Date()), this.nvlCost(current.getExchangeSell(), current.getSell()));
            tsc.addSeries(sellFx);
        }

        return tsc;
    }

    private BigDecimal nvlCost(BigDecimal cost, BigDecimal defaultCost)
    {
        return (cost==null)?defaultCost:cost;
    }
}
