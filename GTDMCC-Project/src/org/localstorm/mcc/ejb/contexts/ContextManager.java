/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.localstorm.mcc.ejb.contexts;

import java.util.Collection;
import org.localstorm.mcc.ejb.except.DuplicateException;
import org.localstorm.mcc.ejb.except.ObjectNotFoundException;
import org.localstorm.mcc.ejb.users.User;

/**
 *
 * @author localstorm
 */
public interface ContextManager 
{
    public void createContext(Context ctx) throws DuplicateException;
    
    public void updateContext(Context ctx);
    
    public Context findById( int id ) throws ObjectNotFoundException;
    
    /* Doesn't return archived contexts */
    public Collection<Context> findByUser(User u) throws ObjectNotFoundException;
}
