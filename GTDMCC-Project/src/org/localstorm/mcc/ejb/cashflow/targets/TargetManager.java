package org.localstorm.mcc.ejb.cashflow.targets;

import org.localstorm.mcc.ejb.cashflow.assets.*;
import java.util.Collection;
import org.localstorm.mcc.ejb.except.ObjectNotFoundException;
import org.localstorm.mcc.ejb.users.User;



/**
 *
 * @author localstorm
 */

public interface TargetManager
{
    public static final String BEAN_NAME="TargetManagerBean";
    
    public void create(Target newTarget, Cost targetCost);

    public Target findById(int targetId) throws ObjectNotFoundException;

    public Collection<Target> findTargets(User user);

    public void remove(Target target);

    public void update(Target target);
}
