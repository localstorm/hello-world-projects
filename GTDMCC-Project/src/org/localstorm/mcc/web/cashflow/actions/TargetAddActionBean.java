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
import org.localstorm.mcc.ejb.cashflow.asset.Cost;
import org.localstorm.mcc.ejb.cashflow.asset.MoneyMathContext;
import org.localstorm.mcc.ejb.cashflow.asset.Target;
import org.localstorm.mcc.ejb.cashflow.asset.ValuableObject;
import org.localstorm.mcc.ejb.users.*;
import org.localstorm.mcc.web.SessionKeys;
import org.localstorm.mcc.web.util.SessionUtil;


/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/AddTarget")
public class TargetAddActionBean extends TargetsEditActionBean {

    @Validate( required=true )
    private String name;

    @After( LifecycleStage.BindingAndValidation ) 
    public void doPostValidationStuff() {
        if ( getContext().getValidationErrors().hasFieldErrors() )
        {
            super.filling();
        }
    }

    @Validate( required=true, minvalue=0, maxvalue=9999999999L )
    private BigDecimal buy;
    
    public String getName() {
        return this.name;
    }
    
    public void setName( String name ) {
        this.name = name;
    }

    public void setBuy(BigDecimal buy) {
        this.buy = buy;
    }

    public BigDecimal getBuy() {
        return buy;
    }

    @DefaultHandler
    public Resolution addContext() throws Exception {
        
        User user = super.getUser();

        Target target = new Target();
        ValuableObject vo = new ValuableObject(user);

        target.setName(name);
        target.setValuable(vo);

        MathContext rounding = MoneyMathContext.ROUNDING;

        Cost cost = new Cost(vo);
        cost.setBuy(RoundUtil.round(this.getBuy(), rounding));
        cost.setSell(RoundUtil.round(this.getBuy(), rounding));

        super.getTargetManager().create(target, cost);

        SessionUtil.clear(super.getSession(), SessionKeys.TARGETS);
            
        
        return new RedirectResolution( TargetsEditActionBean.class );
    }
    
}