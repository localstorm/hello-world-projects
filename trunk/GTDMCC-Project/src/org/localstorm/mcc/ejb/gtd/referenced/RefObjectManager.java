package org.localstorm.mcc.ejb.gtd.referenced;

import java.util.Collection;
import org.localstorm.mcc.ejb.BaseManager;
import org.localstorm.mcc.ejb.gtd.contexts.Context;
import org.localstorm.mcc.ejb.users.User;

/**
 * @author Alexey Kuznetsov
 */
public interface RefObjectManager  extends BaseManager<ReferencedObject>
{
    public static final String BEAN_NAME = "RefObjectManagerBean";

    public Collection<ReferencedObject> findAllArchivedByOwner(User user);
    
    public Collection<ReferencedObject> findAllByOwner( User user );
    
    public Collection<ReferencedObject> findOperativeByOwner( User user, boolean orderByContext );
    
    public Collection<ReferencedObject> findOperativeByContext( Context ctx );
    
}
