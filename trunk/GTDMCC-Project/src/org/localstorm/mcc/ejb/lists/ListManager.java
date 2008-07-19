package org.localstorm.mcc.ejb.lists;

import java.util.Collection;
import org.localstorm.mcc.ejb.contexts.Context;
import org.localstorm.mcc.ejb.except.DuplicateException;
import org.localstorm.mcc.ejb.except.ObjectNotFoundException;

/**
 *
 * @author Alexey Kuznetsov
 */
public interface ListManager 
{
    public void createList(GTDList list) throws DuplicateException;
    
    public void updateList(GTDList list);
    
    /* Doesn't return archived lists */
    public Collection<GTDListType> findAllLists( Context ctx );
    
    public Collection<GTDListType> findAllListTypes( );
    
    public GTDList findById(int id) throws ObjectNotFoundException;
}
