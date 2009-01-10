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
import org.localstorm.mcc.ejb.cashflow.asset.Cost;
import org.localstorm.mcc.ejb.cashflow.asset.ValuableObject;
import org.localstorm.mcc.ejb.users.*;
import org.localstorm.mcc.web.SessionKeys;
import org.localstorm.mcc.web.util.SessionUtil;


/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/AddAsset")
public class AssetAddActionBean extends AssetsEditActionBean {

    @Validate( required=true )
    private String name;

    @After( LifecycleStage.BindingAndValidation ) 
    public void doPostValidationStuff() {
        if ( getContext().getValidationErrors().hasFieldErrors() ) {
            super.filling();
        }
    }

    @Validate( required=true )
    private BigDecimal buy;

    private BigDecimal buyFx;

    @Validate( required=true )
    private BigDecimal sell;

    private BigDecimal sellFx;
    
    public String getName() {
        return this.name;
    }
    
    public void setName( String name ) {
        this.name = name;
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
    
    @DefaultHandler
    public Resolution addContext() {
        
        try {
            User user = super.getUser();
            
            Asset asset = new Asset();
            ValuableObject vo = new ValuableObject(user);

            asset.setName(name);
            asset.setValuable(vo);
            
            MathContext rounding = new MathContext(5);
            
            Cost cost = new Cost(vo);
            cost.setBuy(RoundUtil.round(this.getBuy(), rounding));
            cost.setExchangeBuy(RoundUtil.round(this.getBuyFx(), rounding));
            cost.setSell(RoundUtil.round(this.getSell(), rounding));
            cost.setExchangeSell(RoundUtil.round(this.getSellFx(), rounding));

            super.getAssetManager().create(asset, cost);
            
            SessionUtil.clear(super.getSession(), SessionKeys.ASSETS);
            
        } catch(Exception e) 
        {
            e.printStackTrace();
        }
        
        return new RedirectResolution( AssetsEditActionBean.class );
    }
    
}