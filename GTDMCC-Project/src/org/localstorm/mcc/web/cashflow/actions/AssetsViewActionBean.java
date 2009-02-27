package org.localstorm.mcc.web.cashflow.actions;

import java.math.BigDecimal;
import java.util.Collection;
import org.localstorm.mcc.web.cashflow.*;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.ejb.cashflow.assets.Asset;
import org.localstorm.mcc.ejb.cashflow.assets.AssetManager;
import org.localstorm.mcc.ejb.cashflow.operations.OperationManager;
import org.localstorm.mcc.ejb.cashflow.stat.HistoricalValue;
import org.localstorm.mcc.ejb.cashflow.stat.HistoricalValuesManager;
import org.localstorm.mcc.ejb.cashflow.stat.ValueType;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.cashflow.actions.wrap.AssetWrapper;
import org.localstorm.mcc.web.cashflow.actions.wrap.WrapUtil;

/**
 * @secure-by session (no security check)
 * @author localstorm
 */
@UrlBinding("/actions/cash/nil/ViewAssets")
public class AssetsViewActionBean extends CashflowBaseActionBean {

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
        AssetManager     am = super.getAssetManager();
        OperationManager om = super.getOperationManager();
        User           user = super.getUser();
        
       
        this.assets = am.getAssets(user);
        this.assets = WrapUtil.wrapAssets(assets, om);

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

        HistoricalValue hv  = hvm.getLastHistoricalValue(twc, BigDecimal.ZERO, user);
        HistoricalValue hv2 = hvm.getLastHistoricalValue(twc2, BigDecimal.ZERO, user);

        return !hv.getVal().equals(netWealth) ||
               !hv2.getVal().equals(balance);
    }

}