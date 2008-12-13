package org.localstorm.mcc.web.cashflow.charting;

import java.awt.Color;
import java.awt.Paint;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import org.jfree.chart.ChartFactory;
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

/**
 *
 * @author localstorm
 */
public class AssetCostHistoryChartGenerator {

    private static XYDataset getAssetCostDataset(User user, Integer assetId, Integer daysPeriod) {

        Calendar cal = Calendar.getInstance();

        if ( daysPeriod==null ) {
            cal.add(Calendar.YEAR, -1000); // 1000 years
        } else {
            cal.add(Calendar.DATE, -daysPeriod);
        }

        AssetManager am = ContextLookup.lookup(AssetManager.class, AssetManager.BEAN_NAME);
        Asset asset = am.findAssetById(assetId);
        ValuableObject vo = asset.getValuable();

        if (!user.getId().equals(vo.getOwner().getId())) {
            return null;
        }

        Collection<Cost> costs = am.getCostHistory(vo, cal.getTime());
        Cost current = am.getCurrentCost(vo);

        if (costs.isEmpty())
        {
            Cost cost = new Cost(vo);
            {
                cost.setBuy(current.getBuy());
                cost.setSell(current.getSell());
                cost.setExchangeBuy(current.getExchangeBuy());
                cost.setExchangeSell(current.getExchangeSell());
                cost.setActuationDate(cal.getTime());
            }

            costs = Collections.singletonList(cost);
        }

        TimeSeries sell     = new TimeSeries(asset.getName()+" sell cost");
        TimeSeries buy      = new TimeSeries(asset.getName()+" buy cost");
        TimeSeries buyFx    = new TimeSeries(asset.getName()+" exchange buy cost");
        TimeSeries sellFx   = new TimeSeries(asset.getName()+" exchange sell cost");

        boolean buyFxEnable = false;
        boolean sellFxEnable = false;

        TimeSeriesCollection tsc = new TimeSeriesCollection();

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
                buyFx.addOrUpdate(new Day(c.getActuationDate()), nvlCost(c.getExchangeBuy(), c.getBuy()));
            }
            buyFx.addOrUpdate(new Day(new Date()), nvlCost(current.getExchangeBuy(), current.getBuy()));
            tsc.addSeries(buyFx);
        }

        if (sellFxEnable) {
            for (Cost c: costs) {
                sellFx.addOrUpdate(new Day(c.getActuationDate()), nvlCost(c.getExchangeSell(), c.getSell()));
            }
            sellFx.addOrUpdate(new Day(new Date()), nvlCost(current.getExchangeSell(), current.getSell()));
            tsc.addSeries(sellFx);
        }

        return tsc;
    }

    public static JFreeChart getChart(User user, Integer assetId, Integer daysOffset, String name) {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);

        XYDataset dataset = AssetCostHistoryChartGenerator.getAssetCostDataset(user, assetId, daysOffset);

        JFreeChart chart = ChartFactory.createTimeSeriesChart(name,
                                                              "Time line",
                                                              "Costs",
                                                              dataset,
                                                              true,
                                                              true,
                                                              false);

        XYPlot plot = (XYPlot) chart.getPlot();
        {
            DateAxis axis = (DateAxis) plot.getDomainAxis();
            axis.setDateFormatOverride(new SimpleDateFormat(Constants.REDUCED_DATE_FORMAT));
            plot.setForegroundAlpha(0.7f);
            plot.setNoDataMessage("No data to display");
            plot.setRangeGridlinePaint(Color.WHITE);
            plot.setDomainGridlinePaint(Color.WHITE);
            plot.setBackgroundPaint(Color.LIGHT_GRAY);
        }
        
        return chart;
    }

    private static BigDecimal nvlCost(BigDecimal cost, BigDecimal defaultCost)
    {
        return (cost==null)?defaultCost:cost;
    }
}
