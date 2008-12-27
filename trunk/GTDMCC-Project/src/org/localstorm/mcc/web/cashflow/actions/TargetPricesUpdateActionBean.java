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
import org.localstorm.mcc.ejb.cashflow.asset.AssetManager;
import org.localstorm.mcc.ejb.cashflow.asset.Cost;
import org.localstorm.mcc.ejb.cashflow.asset.Target;
import org.localstorm.mcc.ejb.cashflow.asset.TargetManager;
import org.localstorm.mcc.ejb.cashflow.asset.ValuableObject;
import org.localstorm.mcc.web.cashflow.CashflowBaseActionBean;

/**
 *
 * @author localstorm
 */
@UrlBinding("/actions/UpdateTargetPrice")
public class TargetPricesUpdateActionBean extends CashflowBaseActionBean {

    @Validate(required=true)
    private Integer targetId;

    @Validate( required=true )
    private BigDecimal buy;

    public void setBuy(BigDecimal buy) {
        this.buy = buy;
    }

    public BigDecimal getBuy() {
        return buy;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    @DefaultHandler
    public Resolution update() throws Exception {

        TargetManager tm = super.getTargetManager();
        Target target    = tm.findTargetById(this.getTargetId());

        ValuableObject vo = target.getValuable();

        MathContext rounding = new MathContext(5);

        Cost cost = new Cost(vo);
        {
            cost.setBuy(RoundUtil.round(this.getBuy(), rounding));
            cost.setSell(RoundUtil.round(this.getBuy(), rounding));
        }
        
        vo.setUsedInBalance(false);

        AssetManager am = super.getAssetManager();
        am.updateCost(vo, cost);
        am.updateValuableObject(vo);

        RedirectResolution rr = new RedirectResolution(TargetViewActionBean.class);
        {
            rr.addParameter(TargetViewActionBean.IncommingParameters.TARGET_ID, this.getTargetId());
        }

        return rr;
    }
    

}
