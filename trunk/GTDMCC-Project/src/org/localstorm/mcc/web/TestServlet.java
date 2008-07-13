/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.localstorm.mcc.web;

import java.io.IOException;
import java.io.PrintStream;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import org.localstorm.mcc.ejb.JffRemote;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.ejb.users.UserManagerRemote;

/**
 *
 * @author localstorm
 */
public class TestServlet extends HttpServlet 
{
    //@EJB
    //private JffRemote jff;
    
    @EJB
    private UserManagerRemote umr;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            umr.createUser(new User("newLogin","fname", "lname", "new Hash"));
            //res.getWriter().print("It works (remote!): "+jff.hello());
            res.getWriter().print("It works (remote!)");
        } catch(Exception e) {
            e.printStackTrace(new PrintStream(res.getOutputStream()));
        }
    }
    
    

}
