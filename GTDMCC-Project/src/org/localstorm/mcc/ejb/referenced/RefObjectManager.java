package org.localstorm.mcc.ejb.referenced;

import java.util.Collection;
import org.localstorm.mcc.ejb.BaseManager;
import org.localstorm.mcc.ejb.contexts.Context;
import org.localstorm.mcc.ejb.users.User;

/**
 *
 * @author Alexey Kuznetsov
 */
public interface RefObjectManager  extends BaseManager<ReferencedObject>
{
    public static final String BEAN_NAME = "RefObjectManagerBean";
    //TODO!
    public Collection<ReferencedObject> findOperativeByUser( User user );
    
    public Collection<ReferencedObject> findByContext(Context ctx);
    
}
