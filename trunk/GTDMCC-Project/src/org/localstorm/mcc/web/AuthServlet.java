package org.localstorm.mcc.web;

import org.localstorm.mcc.web.gtd.Views;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.users.*;

/**
 *
 * @author Alexey Kuznetsov
 */
public class AuthServlet extends HttpServlet 
{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        this.doPost(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        
        String login = req.getParameter(CGIParams.AUTH_LOGIN);
        String pwd   = req.getParameter(CGIParams.AUTH_PASSWORD);
                
        UserManager um = ContextLookup.lookup(UserManager.class, 
                                              UserManager.BEAN_NAME);
        User u = um.login(login, pwd);
        HttpSession sess = req.getSession(true);

        if (u!=null) {
            sess.setAttribute(SessionKeys.USER, u);
            res.sendRedirect("actions/Index");
        } else {
            req.getRequestDispatcher(Views.LOGIN).forward(req, res);
            return;
        }
    }

    
}
