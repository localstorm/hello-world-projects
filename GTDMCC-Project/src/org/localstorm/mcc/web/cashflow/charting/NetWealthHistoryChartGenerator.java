package org.localstorm.mcc.web.cashflow.charting;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;
import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.cashflow.asset.Asset;
import org.localstorm.mcc.ejb.cashflow.asset.AssetManager;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.Constants;
import org.localstorm.mcc.web.cashflow.actions.wrap.AssetWrapper;
import org.localstorm.mcc.web.cashflow.actions.wrap.WrapUtil;


/**
 *
 * @author localstorm
 */
public class NetWealthHistoryChartGenerator {

    public static JFreeChart getChart(User user) {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
        String curDate = sdf.format(new Date());
        JFreeChart chart = ChartFactory.createPieChart3D("Total wealth chart (" + curDate + ")", 
                                                                getWealthDataset(user),
                                                                true,
                                                                true,
                                                                false);

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        {
            plot.setDarkerSides(true);
            plot.setIgnoreZeroValues(true);
            plot.setCircular(true);
            plot.setStartAngle(120);
            plot.setDirection(Rotation.CLOCKWISE);
            plot.setForegroundAlpha(0.7f);
            plot.setNoDataMessage("No data to display");
        }
        
        return chart;
    }

    private static PieDataset getWealthDataset(User user) {

        AssetManager am = ContextLookup.lookup(AssetManager.class, AssetManager.BEAN_NAME);
        Collection<Asset> assets = am.findAssetsByOwner(user);
        assets = WrapUtil.wrapAssets(assets, am);

        DefaultPieDataset result = new DefaultPieDataset();

        for (Asset a: assets) {

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
