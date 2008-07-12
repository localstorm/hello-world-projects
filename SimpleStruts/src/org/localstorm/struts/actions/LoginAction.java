package org.localstorm.struts.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.localstorm.struts.forms.LogonActionForm;


public class LoginAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession sess = req.getSession(true);
		if (sess.getAttribute("user")==null){
			
			LogonActionForm laf = (LogonActionForm)form;

			String login = laf.getLogin();
			String password = laf.getPassword(); 
			
			if (login.length()!=0 && password.length()!=0){
				if (login.equals("a") && password.equals("b")){
					sess.setAttribute("user", new String("a"));
					return mapping.findForward("success");
				}else{
					return mapping.findForward("failure");
				}				
			}
		}

		sess.invalidate();
		return mapping.findForward("failure");
	}
}
