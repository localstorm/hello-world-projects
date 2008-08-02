package org.localstorm.mcc.ejb.lists;

import java.util.Collection;
import org.localstorm.mcc.ejb.BaseManager;
import org.localstorm.mcc.ejb.contexts.Context;

/**
 *
 * @author Alexey Kuznetsov
 */
public interface ListManager extends BaseManager<GTDList>
{
    public static final String BEAN_NAME = "ListManagerBean";
    
    /* Doesn't return archived lists */
    public Collection<GTDList> findByContext( Context ctx );
    
    public Collection<GTDList> findByContextArchived(Context ctx);
}
