package org.localstorm.mcc.ejb.gtd.contexts;

import java.util.Collection;
import java.util.List;
import org.localstorm.mcc.ejb.BaseManager;

import org.localstorm.mcc.ejb.users.User;

/**
 *
 * @author localstorm
 */

public interface ContextManager extends BaseManager<Context> 
{
    public static final String BEAN_NAME="ContextManagerBean";
    
    /* Doesn't return archived contexts */
    public Collection<Context> find(User u);

    public List<Context> findArchived(User u);
}
