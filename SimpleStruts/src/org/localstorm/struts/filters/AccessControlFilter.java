package org.localstorm.struts.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AccessControlFilter implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		try{
			HttpServletRequest 	requ = (HttpServletRequest)req;
			
			HttpSession sess = requ.getSession(true);
			if (sess.getAttribute("user")==null){
				HttpServletResponse resp = (HttpServletResponse)res;
				resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);;
			}else{
				chain.doFilter(req, res);
			}
		}catch(ClassCastException e){
			throw new ServletException(e);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
