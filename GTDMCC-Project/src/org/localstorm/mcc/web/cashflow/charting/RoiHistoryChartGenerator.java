package org.localstorm.mcc.web.cashflow.charting;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.cashflow.HistoricalValuesManager;
import org.localstorm.mcc.ejb.cashflow.entity.HistoricalValue;
import org.localstorm.mcc.ejb.cashflow.entity.ValueType;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.Constants;

import java.awt.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author localstorm
 */
public class RoiHistoryChartGenerator {

     private static XYDataset getRoiHistoryDataset(User user, Integer daysPeriod) {

        Calendar cal = Calendar.getInstance();

        if ( daysPeriod==null ) {
            cal.add(Calendar.YEAR, -50); // 50 years
        } else {
            cal.add(Calendar.DATE, -daysPeriod);
        }

        HistoricalValuesManager hvm = ContextLookup.lookup(HistoricalValuesManager.class,
                                                           HistoricalValuesManager.BEAN_NAME);
        
        Collection<HistoricalValue> hvs = hvm.getHistory(ValueType.BALANCE_CHECKPOINT,
                                                             user,
                                                             cal.getTime());

        HistoricalValue last  = hvm.getLastHistoricalValue(ValueType.BALANCE_CHECKPOINT,
                                                       BigDecimal.ZERO,
                                                       user);

        Date minDate = new Date();

        if (hvs.isEmpty()) {
            HistoricalValue first = new HistoricalValue();
            {
                first.setFixDate(cal.getTime());
                first.setObjectId(null);
                first.setOwner(user);
                first.setVal(last.getVal());
                first.setValueTag(ValueType.BALANCE_CHECKPOINT);
            }
            hvs.add(first);
        }

        HistoricalValue right = new HistoricalValue();
        {
            right.setFixDate(new Date());
            right.setObjectId(null);
            right.setOwner(user);
            right.setVal(last.getVal());
            right.setValueTag(ValueType.BALANCE_CHECKPOINT);
        }

        hvs.add(right);
  
        TimeSeries balance     = new TimeSeries("Balance");

        TimeSeriesCollection tsc = new TimeSeriesCollection();

        for (HistoricalValue hv : hvs) {
            Date fixDate = hv.getFixDate();
            balance.addOrUpdate(new Day(fixDate), hv.getVal());

            if (minDate.after(fixDate))
            {
                minDate = fixDate;
            }
        }

        tsc.addSeries(balance);

        return tsc;
    }

    public static JFreeChart getChart(User user, Integer daysOffset, String name) {
        XYDataset dataset = getRoiHistoryDataset(user, daysOffset);

        JFreeChart chart = ChartFactory.createTimeSeriesChart(name,
                                                              "Time line",
                                                              "Money",
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
            plot.setRangeGridlinePaint(Color.BLUE);
            plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
            plot.setBackgroundPaint(Color.WHITE);

            XYItemRenderer ir = plot.getRenderer();
            ir.setBaseStroke(new BasicStroke(2.0f));
        }

        return chart;
    }

}
