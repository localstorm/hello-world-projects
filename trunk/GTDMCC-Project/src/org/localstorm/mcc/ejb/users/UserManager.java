/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.localstorm.mcc.ejb.users;

import org.localstorm.mcc.ejb.except.DuplicateException;
import org.localstorm.mcc.ejb.except.ObjectNotFoundException;

/**
 *
 * @author localstorm
 */
public interface UserManager 
{
    public void createUser(User user) throws DuplicateException;
    
    public void updateUser(User user);
    
    public User findById( int id ) throws ObjectNotFoundException;
}
