package org.localstorm.mcc.web.cashflow.actions;

import java.math.BigDecimal;
import java.util.Collection;
import org.localstorm.mcc.web.cashflow.*;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.ejb.cashflow.asset.Asset;
import org.localstorm.mcc.ejb.cashflow.asset.AssetManager;
import org.localstorm.mcc.ejb.cashflow.stat.HistoricalValue;
import org.localstorm.mcc.ejb.cashflow.stat.HistoricalValuesManager;
import org.localstorm.mcc.ejb.cashflow.stat.ValueType;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.cashflow.actions.wrap.AssetWrapper;
import org.localstorm.mcc.web.cashflow.actions.wrap.WrapUtil;

@UrlBinding("/actions/ViewAssets")
public class ViewAssetsActionBean extends CashflowBaseActionBean {

    private Collection<Asset> assets;
    private BigDecimal        netWealth;
    private BigDecimal        balance;
    private boolean           checkpointUpdateNeeded;

    public Collection<Asset> getAssets() {
        return assets;
    }

    public void setAssets(Collection<Asset> assets) {
        this.assets = assets;
    }

    public BigDecimal getNetWealth()
    {
        return this.netWealth;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public boolean isCheckpointUpdateNeeded() {
        return checkpointUpdateNeeded;
    }

    @DefaultHandler
    public Resolution filling() {
        AssetManager am = super.getAssetManager();
        User user = super.getUser();
        
       
        this.assets = am.findAssetsByOwner(user);
        this.assets = WrapUtil.wrapAssets(assets, am);

        this.netWealth = BigDecimal.ZERO;
        this.balance   = BigDecimal.ZERO;

        for (Asset a: assets)
        {
            AssetWrapper aw = (AssetWrapper) a;
            this.netWealth = this.netWealth.add(aw.getNetWealth());

            if (aw.getValuable().isUsedInBalance()) {
                this.balance   = this.balance.add(aw.getBalance());
            }
        }

        this.checkpointUpdateNeeded = this.getCheckpointStatus(user,
                                                               this.netWealth,
                                                               this.balance);

        return new ForwardResolution(Views.ASSETS);
    }

    private boolean getCheckpointStatus(User user,
                                        BigDecimal netWealth,
                                        BigDecimal balance) {
        HistoricalValuesManager hvm = super.getHistoricalValuesManager();

        ValueType twc  = ValueType.NET_WEALTH_CHECKPOINT;
        ValueType twc2 = ValueType.BALANCE_CHECKPOINT;

        HistoricalValue hv  = hvm.findLastByValueTag(twc, BigDecimal.ZERO, user);
        HistoricalValue hv2 = hvm.findLastByValueTag(twc2, BigDecimal.ZERO, user);

        return !hv.getVal().equals(netWealth) ||
               !hv2.getVal().equals(balance);
    }

}