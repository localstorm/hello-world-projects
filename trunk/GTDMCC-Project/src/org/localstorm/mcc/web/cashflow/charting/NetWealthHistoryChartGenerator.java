package org.localstorm.mcc.web.cashflow.charting;

import java.awt.Color;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
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
import org.localstorm.mcc.ejb.cashflow.stat.HistoricalValue;
import org.localstorm.mcc.ejb.cashflow.stat.HistoricalValuesManager;
import org.localstorm.mcc.ejb.cashflow.stat.ValueType;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.Constants;

/**
 *
 * @author localstorm
 */
public class NetWealthHistoryChartGenerator {

     private static XYDataset getNetWealthDataset(User user, Integer daysPeriod) {

        Calendar cal = Calendar.getInstance();

        if ( daysPeriod==null ) {
            cal.add(Calendar.YEAR, -50); // 50 years
        } else {
            cal.add(Calendar.DATE, -daysPeriod);
        }

        HistoricalValuesManager hvm = ContextLookup.lookup(HistoricalValuesManager.class,
                                                           HistoricalValuesManager.BEAN_NAME);
        
        Collection<HistoricalValue> hvs = hvm.findByValueTag(ValueType.NET_WEALTH_CHECKPOINT,
                                                             user,
                                                             cal.getTime());

        HistoricalValue last  = hvm.findLastByValueTag(ValueType.NET_WEALTH_CHECKPOINT, 
                                                       BigDecimal.ZERO,
                                                       user);

        if (hvs.isEmpty()) {
            HistoricalValue first = new HistoricalValue();
            {
                first.setFixDate(cal.getTime());
                first.setObjectId(null);
                first.setOwner(user);
                first.setVal(last.getVal());
                first.setValueTag(ValueType.NET_WEALTH_CHECKPOINT);
            }
            hvs.add(first);
        }

        HistoricalValue right = new HistoricalValue();
        {
            right.setFixDate(new Date());
            right.setObjectId(null);
            right.setOwner(user);
            right.setVal(last.getVal());
            right.setValueTag(ValueType.NET_WEALTH_CHECKPOINT);
        }

        hvs.add(right);
  
        TimeSeries netWealth     = new TimeSeries("Net Wealth");

        TimeSeriesCollection tsc = new TimeSeriesCollection();

        for (HistoricalValue hv : hvs) {
            Date fixDate = hv.getFixDate();
            netWealth.addOrUpdate(new Day(fixDate), hv.getVal());
        }

        tsc.addSeries(netWealth);

        return tsc;
    }

    public static JFreeChart getChart(User user, Integer daysOffset, String name) {
        XYDataset dataset = getNetWealthDataset(user, daysOffset);

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

}
