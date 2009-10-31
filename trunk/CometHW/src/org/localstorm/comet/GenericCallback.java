package org.localstorm.comet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface GenericCallback {

	public void handle(HttpServletRequest request, HttpServletResponse response)
    	throws IOException, ServletException;
	
}
