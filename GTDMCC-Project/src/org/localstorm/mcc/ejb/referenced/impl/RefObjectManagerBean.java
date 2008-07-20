package org.localstorm.mcc.ejb.referenced.impl;

import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.localstorm.mcc.ejb.Constants;
import org.localstorm.mcc.ejb.contexts.Context;
import org.localstorm.mcc.ejb.except.DuplicateException;
import org.localstorm.mcc.ejb.except.ObjectNotFoundException;
import org.localstorm.mcc.ejb.lists.GTDList;
import org.localstorm.mcc.ejb.referenced.RefObjectManager;
import org.localstorm.mcc.ejb.referenced.RefObjectManagerLocal;
import org.localstorm.mcc.ejb.referenced.RefObjectManagerRemote;
import org.localstorm.mcc.ejb.referenced.ReferencedObject;
import org.localstorm.mcc.ejb.tasks.Task;
import org.localstorm.mcc.ejb.users.User;

/**
 *
 * @author Alexey Kuznetsov
 */

/**
 *
 * @author Alexey Kuznetsov
 */
@Stateless
public class RefObjectManagerBean implements RefObjectManagerLocal, RefObjectManagerRemote
{

    @Override
    public void createObject(ReferencedObject obj) throws DuplicateException 
    {
        try 
        {
            em.persist( obj );
        } catch(EntityExistsException e) 
        {
            throw new DuplicateException(e);
        }
    }

    @Override
    public void updateObject(Object obj) 
    {
        em.merge(obj);
    }
    
    @Override
    public Collection<ReferencedObject> findByContext(Context ctx) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public RefObjectManager findById(int id) throws ObjectNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<ReferencedObject> findByTask(GTDList list) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<ReferencedObject> findByTask(Task task) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<ReferencedObject> findOperativeByUser(User user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @PersistenceContext(unitName=Constants.DEFAULT_PU)
    private EntityManager em;
}
