package org.localstorm.mcc.ejb.gtd;

import org.localstorm.mcc.ejb.BaseManager;
import org.localstorm.mcc.ejb.gtd.entity.Context;
import org.localstorm.mcc.ejb.users.User;

import java.util.Collection;
import java.util.List;

/**
 *
 * @author localstorm
 */

public interface ContextManager extends BaseManager<Context> 
{
    public static final String BEAN_NAME="ContextManagerBean";
    
    /* Doesn't return archived contexts */
    public Collection<Context> getContexts(User u);

    public List<Context> getArchived(User u);
}
