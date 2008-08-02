/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.localstorm.mcc.ejb.users.impl;

import javax.ejb.Stateless;
import org.localstorm.mcc.ejb.AbstractManager;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.ejb.users.UserManagerLocal;
import org.localstorm.mcc.ejb.users.UserManagerRemote;

/**
 *
 * @author localstorm
 */
@Stateless
public class UserManagerBean extends AbstractManager<User> 
                             implements UserManagerLocal, UserManagerRemote 
{
    public UserManagerBean() {
        super(User.class);
    }
    
}
