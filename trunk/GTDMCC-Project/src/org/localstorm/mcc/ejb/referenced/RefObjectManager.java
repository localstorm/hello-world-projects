package org.localstorm.mcc.ejb.referenced;

import java.util.Collection;
import org.localstorm.mcc.ejb.contexts.Context;
import org.localstorm.mcc.ejb.except.DuplicateException;
import org.localstorm.mcc.ejb.except.ObjectNotFoundException;
import org.localstorm.mcc.ejb.lists.GTDList;
import org.localstorm.mcc.ejb.tasks.Task;
import org.localstorm.mcc.ejb.users.User;

/**
 *
 * @author Alexey Kuznetsov
 */
public interface RefObjectManager 
{
    public void createObject(ReferencedObject obj) throws DuplicateException;
    
    public void updateObject(Object task);
    
    public RefObjectManager findById( int id ) throws ObjectNotFoundException;

    
    //TODO!
    public Collection<ReferencedObject> findOperativeByUser( User user );
    
    public Collection<ReferencedObject> findByContext(Context ctx);
    
    public Collection<ReferencedObject> findByTask(Task task);
    
    public Collection<ReferencedObject> findByTask(GTDList list);
    
    
}
