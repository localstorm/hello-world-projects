/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.localstorm.mcc.web.cashflow.actions;

import java.math.BigDecimal;
import java.math.MathContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.cashflow.asset.Asset;
import org.localstorm.mcc.ejb.cashflow.asset.AssetManager;
import org.localstorm.mcc.ejb.cashflow.asset.Cost;
import org.localstorm.mcc.ejb.cashflow.asset.ValuableObject;
import org.localstorm.mcc.web.cashflow.CashflowBaseActionBean;

/**
 *
 * @author localstorm
 */
@UrlBinding("/actions/UpdateAssetPrices")
public class AssetPricesUpdateActionBean extends CashflowBaseActionBean {

    @Validate(required=true)
    private Integer assetId;

    @Validate( required=true )
    private BigDecimal buy;

    private BigDecimal buyFx;

    @Validate( required=true )
    private BigDecimal sell;

    private BigDecimal sellFx;

    private boolean usedInBalance;

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

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public boolean isUsedInBalance() {
        return usedInBalance;
    }

    public void setUsedInBalance(boolean usedInBalance) {
        this.usedInBalance = usedInBalance;
    }

    @DefaultHandler
    public Resolution update() throws Exception {

        AssetManager am = super.getAssetManager();
        Asset asset = am.findAssetById(this.getAssetId());
        ValuableObject vo = asset.getValuable();

        MathContext rounding = new MathContext(5);

        Cost cost = new Cost(vo);
        {
            cost.setBuy(RoundUtil.round(this.getBuy(), rounding));
            cost.setExchangeBuy(RoundUtil.round(this.getBuyFx(), rounding));
            cost.setSell(RoundUtil.round(this.getSell(), rounding));
            cost.setExchangeSell(RoundUtil.round(this.getSellFx(), rounding));
        }
        
        vo.setUsedInBalance(this.isUsedInBalance());

        am.updateCost(vo, cost);
        am.updateValuableObject(vo);

        RedirectResolution rr = new RedirectResolution(AssetViewActionBean.class);
        {
            rr.addParameter(AssetViewActionBean.IncommingParameters.ASSET_ID, this.getAssetId());
        }

        return rr;
    }
    

}
