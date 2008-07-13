/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.localstorm.mcc.ejb.users.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.localstorm.mcc.ejb.Constants;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.ejb.users.UserManagerLocal;
import org.localstorm.mcc.ejb.users.UserManagerRemote;
import org.localstorm.mcc.ejb.except.DuplicateException;
import org.localstorm.mcc.ejb.except.ObjectNotFoundException;

/**
 *
 * @author localstorm
 */
@Stateless
public class UserManagerBean implements UserManagerLocal, UserManagerRemote {

    @Override
    public void updateUser( User user ) 
    {
        em.merge( user );
    }
    
    @Override
    public void createUser(User user) throws DuplicateException 
    {
        try 
        {
            em.persist(user);
        } catch(EntityExistsException e) 
        {
            throw new DuplicateException(e);
        }
    }

    @Override
    public User findById(int id) throws ObjectNotFoundException 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @PersistenceContext(unitName=Constants.DEFAULT_PU)
    private EntityManager em;
}
