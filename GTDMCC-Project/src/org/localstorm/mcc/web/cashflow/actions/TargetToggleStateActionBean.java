package org.localstorm.mcc.web.cashflow.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

import org.localstorm.mcc.ejb.cashflow.asset.Target;
import org.localstorm.mcc.ejb.cashflow.asset.TargetManager;
import org.localstorm.mcc.web.SessionKeys;
import org.localstorm.mcc.web.cashflow.CashflowBaseActionBean;
import org.localstorm.mcc.web.util.SessionUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ToggleStateTarget")
public class TargetToggleStateActionBean extends CashflowBaseActionBean
{
    @Validate( required=true )
    private int targetId;

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    
    @DefaultHandler
    public Resolution toggle() throws Exception {

        TargetManager tm = super.getTargetManager();
        Target target = tm.findTargetById(this.getTargetId());

        
        target.setArchived(!target.isArchived());
        
        tm.update(target);
        
        SessionUtil.clear(getSession(), SessionKeys.TARGETS);
        return new RedirectResolution(TargetsEditActionBean.class);
    }
}
