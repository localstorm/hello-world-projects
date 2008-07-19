/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.localstorm.mcc.ejb.lists.impl;

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
import org.localstorm.mcc.ejb.lists.GTDListType;
import org.localstorm.mcc.ejb.lists.ListManagerLocal;
import org.localstorm.mcc.ejb.lists.ListManagerRemote;
import org.localstorm.mcc.ejb.users.User;

/**
 *
 * @author localstorm
 */
@Stateless
public class ListManagerBean implements ListManagerLocal, ListManagerRemote 
{
    
    @Override
    public void createList(GTDList list) throws DuplicateException 
    {
        try 
        {
            em.persist( list );
        } catch(EntityExistsException e) 
        {
            throw new DuplicateException(e);
        }
    }

    @Override
    public void updateList(GTDList list)
    {
        em.merge( list );
    }
    
    @Override
    public GTDList findById(int id) throws ObjectNotFoundException 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<GTDListType> findAllListTypes() 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<GTDListType> findAllLists(Context ctx) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @PersistenceContext(unitName=Constants.DEFAULT_PU)
    private EntityManager em;
}
