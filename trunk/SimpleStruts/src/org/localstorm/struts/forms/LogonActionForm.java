package org.localstorm.struts.forms;

import org.apache.struts.action.ActionForm;

@SuppressWarnings("serial")
public class LogonActionForm extends ActionForm {
	private String login;
	private String password;
	
	public LogonActionForm() {
		login = "";
		password = "";
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
