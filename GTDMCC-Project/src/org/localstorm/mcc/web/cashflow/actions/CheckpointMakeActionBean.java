package org.localstorm.mcc.web.cashflow.actions;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.ejb.cashflow.asset.Asset;
import org.localstorm.mcc.ejb.cashflow.asset.AssetManager;
import org.localstorm.mcc.ejb.cashflow.stat.HistoricalValue;
import org.localstorm.mcc.ejb.cashflow.stat.HistoricalValuesManager;
import org.localstorm.mcc.ejb.cashflow.stat.ValueType;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.cashflow.CashflowBaseActionBean;
import org.localstorm.mcc.web.cashflow.actions.wrap.AssetWrapper;
import org.localstorm.mcc.web.cashflow.actions.wrap.WrapUtil;

/**
 *
 * @author localstorm
 */
@UrlBinding("/actions/MakeCheckpoint")
public class CheckpointMakeActionBean extends CashflowBaseActionBean
{
    @DefaultHandler
    public Resolution filling() {
        User user                   = super.getUser();
        AssetManager am             = super.getAssetManager();
        HistoricalValuesManager hvm = super.getHistoricalValuesManager();

        Collection<Asset> assets = am.findAssetsByOwner(user);
        assets = WrapUtil.wrapAssets(assets, am);

        BigDecimal netWealth = BigDecimal.ZERO;
        BigDecimal balance   = BigDecimal.ZERO;

        for (Asset a: assets)
        {
            AssetWrapper aw = (AssetWrapper) a;
            netWealth = netWealth.add(aw.getNetWealth());
            balance   = balance.add(aw.getBalance());
        }


        HistoricalValue netWealthHV = new HistoricalValue();
        {
            netWealthHV.setFixDate(new Date());
            netWealthHV.setObjectId(null);
            netWealthHV.setOwner(user);
            netWealthHV.setValueTag(ValueType.NET_WEALTH_CHECKPOINT);
            netWealthHV.setVal(netWealth);
        }

        HistoricalValue balanceHV = new HistoricalValue();
        {
            balanceHV.setFixDate(new Date());
            balanceHV.setObjectId(null);
            balanceHV.setOwner(user);
            balanceHV.setValueTag(ValueType.BALANCE_CHECKPOINT);
            balanceHV.setVal(balance);
        }

        hvm.log(netWealthHV);
        hvm.log(balanceHV);

        return new RedirectResolution(ViewAssetsActionBean.class);
    }

}
