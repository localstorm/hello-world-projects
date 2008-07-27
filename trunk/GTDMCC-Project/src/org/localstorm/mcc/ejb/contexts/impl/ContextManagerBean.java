/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.localstorm.mcc.ejb.contexts.impl;

import java.util.Collection;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.localstorm.mcc.ejb.Constants;
import org.localstorm.mcc.ejb.contexts.Context;
import org.localstorm.mcc.ejb.contexts.ContextManager;
import org.localstorm.mcc.ejb.except.DuplicateException;
import org.localstorm.mcc.ejb.except.ObjectNotFoundException;
import org.localstorm.mcc.ejb.users.User;

/**
 *
 * @author localstorm
 */
@Stateless
@Local(ContextManager.class)
//@Remote(ContextManagerRemote.class)
public class ContextManagerBean implements ContextManager//Local, ContextManagerRemote 
{

    @Override
    public void createContext(Context ctx) throws DuplicateException 
    {
        try 
        {
            em.persist( ctx );
        } catch(EntityExistsException e) 
        {
            throw new DuplicateException(e);
        }
    }

    @Override
    public void updateContext( Context ctx ) 
    {
        em.merge( ctx );
    }
    
    @Override
    public Context findById(int id) throws ObjectNotFoundException 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Collection<Context> findByUser(User u) throws ObjectNotFoundException 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @PersistenceContext(unitName=Constants.DEFAULT_PU)
    private EntityManager em;
}
