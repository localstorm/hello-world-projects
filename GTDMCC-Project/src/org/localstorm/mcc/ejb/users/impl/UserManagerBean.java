/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.localstorm.mcc.ejb.users.impl;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
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

    @Override
    public User login(String login, String pwd) 
    {
        Query uq = em.createNamedQuery(User.Queries.FIND_BY_LOGIN_AND_PASS);
        uq.setParameter(User.Properties.LOGIN, login);
        uq.setParameter(User.Properties.PASSWORD, pwd);
        System.out.println("LOGIN:"+login);
        System.out.println("pwd:"+pwd);
        
        List<User> l = uq.getResultList();
        if ( l.isEmpty() ) {
            return null;
        } else {
            User user  = (User) l.get(0);
            return user;
        }
        
    }
    
    
}
