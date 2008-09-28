package org.localstorm.mcc.ejb.referenced;

import java.util.Collection;
import org.localstorm.mcc.ejb.BaseManager;
import org.localstorm.mcc.ejb.contexts.Context;
import org.localstorm.mcc.ejb.users.User;

/**
 * @author Alexey Kuznetsov
 */
public interface RefObjectManager  extends BaseManager<ReferencedObject>
{
    public static final String BEAN_NAME = "RefObjectManagerBean";

    public Collection<ReferencedObject> findAllArchivedByOwner(User user);
    
    public Collection<ReferencedObject> findAllByOwner( User user );
    
    public Collection<ReferencedObject> findOperativeByOwner( User user );
    
    public Collection<ReferencedObject> findOperativeByContext( Context ctx );
    
}
