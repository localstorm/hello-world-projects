/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.localstorm.mcc.ejb.users;

import org.localstorm.mcc.ejb.*;

/**
 *
 * @author localstorm
 */
public interface UserManager
{
    public static final String BEAN_NAME="UserManagerBean";

    public User login(String login, String pwd);
    
    public boolean subscribe(String login, String pwd);
    
}
