package org.localstorm.mcc.web.cashflow.actions;

import org.localstorm.mcc.web.util.RoundUtil;
import java.math.BigDecimal;
import java.math.MathContext;
import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.cashflow.asset.Asset;
import org.localstorm.mcc.ejb.cashflow.asset.AssetManager;
import org.localstorm.mcc.ejb.cashflow.asset.Cost;
import org.localstorm.mcc.ejb.cashflow.asset.MoneyMathContext;
import org.localstorm.mcc.ejb.cashflow.asset.ValuableObject;
import org.localstorm.mcc.web.SessionKeys;
import org.localstorm.mcc.web.util.SessionUtil;

/**
 *
 * @author localstorm
 */
@UrlBinding("/actions/UpdateAsset")
public class AssetUpdateActionBean extends AssetViewActionBean {

    @Validate( required=true, minvalue=0, maxvalue=9999999999L )
    private BigDecimal buy;

    @Validate( minvalue=0, maxvalue=9999999999L )
    private BigDecimal buyFx;

    @Validate( required=true, minvalue=0, maxvalue=9999999999L )
    private BigDecimal sell;

    @Validate( minvalue=0, maxvalue=9999999999L )
    private BigDecimal sellFx;

    @Validate( required=true )
    private String name;

    private boolean usedInBalance;

    @After( LifecycleStage.BindingAndValidation )
    public void doPostValidationStuff() throws Exception {
        if ( getContext().getValidationErrors().hasFieldErrors() )
        {
            super.filling();
        }
    }
    
    public void setBuy(BigDecimal buy) {
        this.buy = buy;
    }

    public void setBuyFx(BigDecimal buyFx) {
        this.buyFx = buyFx;
    }

    public void setSell(BigDecimal sell) {
        this.sell = sell;
    }

    public void setSellFx(BigDecimal sellFx) {
        this.sellFx = sellFx;
    }

    public BigDecimal getBuy() {
        return buy;
    }

    public BigDecimal getBuyFx() {
        return buyFx;
    }

    public BigDecimal getSell() {
        return sell;
    }

    public BigDecimal getSellFx() {
        return sellFx;
    }

    public boolean isUsedInBalance() {
        return usedInBalance;
    }

    public void setUsedInBalance(boolean usedInBalance) {
        this.usedInBalance = usedInBalance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @DefaultHandler
    public Resolution update() throws Exception {

        AssetManager am = super.getAssetManager();
        Asset asset = am.findAssetById(this.getAssetId());
        ValuableObject vo = asset.getValuable();

        
        MathContext rounding = MoneyMathContext.ROUNDING;

        Cost cost = new Cost(vo);
        {
            cost.setBuy(RoundUtil.round(this.getBuy(), rounding));
            cost.setExchangeBuy(RoundUtil.round(this.getBuyFx(), rounding));
            cost.setSell(RoundUtil.round(this.getSell(), rounding));
            cost.setExchangeSell(RoundUtil.round(this.getSellFx(), rounding));
        }

        vo.setUsedInBalance(this.isUsedInBalance());
        asset.setName(this.getName());

        am.update(asset);
        am.updateCost(vo, cost);
        am.updateValuableObject(vo);

        SessionUtil.clear(super.getSession(), SessionKeys.ASSETS);

        RedirectResolution rr = new RedirectResolution(AssetViewActionBean.class);
        {
            rr.addParameter(AssetViewActionBean.IncommingParameters.ASSET_ID, this.getAssetId());
        }

        return rr;
    }
    

}
