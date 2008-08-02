/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.localstorm.mcc.ejb.contexts;

import java.util.Collection;
import java.util.List;
import org.localstorm.mcc.ejb.BaseManager;

import org.localstorm.mcc.ejb.users.User;

/**
 *
 * @author localstorm
 */

public interface ContextManager extends BaseManager<Context> 
{
    public static final String BEAN_NAME="ContextManagerBean";
    
    /* Doesn't return archived contexts */
    public Collection<Context> findByOwner(User u);

    public List<Context> findByOwnerArchived(User u);
}
