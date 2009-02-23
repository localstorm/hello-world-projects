package org.localstorm.mcc.web.cashflow.actions.wrap;

import org.localstorm.mcc.ejb.cashflow.assets.Cost;
import org.localstorm.mcc.ejb.cashflow.targets.Target;
import org.localstorm.mcc.ejb.cashflow.assets.ValuableObject;

/**
 *
 * @author localstorm
 */
public class TargetWrapper extends Target {

    private Target target;
    private Cost cost;

    public TargetWrapper(Target target,
                         Cost cost)
    {
        if (cost==null) {
            throw new NullPointerException("Cost is null!");
        }
        
        this.target = target;
        this.cost   = cost;
    }

    @Override
    public Integer getId() {
        return target.getId();
    }

    @Override
    public String getName() {
        return target.getName();
    }

    @Override
    public ValuableObject getValuable() {
        return target.getValuable();
    }

    @Override
    public void setName(String name) {
        target.setName(name);
    }

    @Override
    public void setValuable(ValuableObject valuable) {
        target.setValuable(valuable);
    }

    @Override
    public boolean isArchived() {
        return target.isArchived();
    }

    @Override
    public void setArchived(boolean archived) {
        this.target.setArchived(archived);
    }

    public Cost getCurrentCost() {
        return this.cost;
    }
   
}
