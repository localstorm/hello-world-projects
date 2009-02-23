package org.localstorm.mcc.web.cashflow.actions;

import java.util.Collection;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.localstorm.mcc.ejb.cashflow.targets.Target;
import org.localstorm.mcc.ejb.cashflow.targets.TargetManager;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.cashflow.CashflowBaseActionBean;
import org.localstorm.mcc.web.cashflow.Views;

/**
 * @secure-by session
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/EditTargets")
public class TargetsEditActionBean extends CashflowBaseActionBean {

    private Collection<Target> archiveTargets;

    public Collection<Target> getArchiveTargets() {
        return archiveTargets;
    }

    public void setArchiveTargets(Collection<Target> archiveTargets) {
        this.archiveTargets = archiveTargets;
    }

    @DefaultHandler
    public Resolution filling() {
        
        TargetManager tm = super.getTargetManager();
        User user = super.getUser();

        this.setArchiveTargets(tm.findTargetsByOwner(user));
        
        return new ForwardResolution(Views.EDIT_TARGETS);
    }
    
    
}