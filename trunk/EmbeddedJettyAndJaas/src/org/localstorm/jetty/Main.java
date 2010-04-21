package org.localstorm.jetty;

import java.io.File;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.ContextHandler;
import org.mortbay.jetty.handler.DefaultHandler;
import org.mortbay.jetty.handler.HandlerCollection;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.security.*;
import org.mortbay.jetty.servlet.DefaultServlet;
import org.mortbay.jetty.servlet.ServletHandler;
import org.mortbay.jetty.servlet.ServletHolder;

public class Main {

	private static HandlerCollection handlers;

	public static void main(String... args) throws Exception {

		Server server = new Server();

		Connector connector = new SelectChannelConnector();
		connector.setPort(8080);
		server.setConnectors(new Connector[] { connector });

		
		/*
		 * WebAppContext webappcontext = new WebAppContext();
		 * webappcontext.setContextPath("/mywebapp");
		 * webappcontext.setWar("./path/to/my/war/orExplodedwar");
		 * webappcontext.addHandler(sh);
		 */

		handlers = new HandlerCollection();
		handlers.setHandlers(new Handler[] { });
		
		addDocRoot("/", new File("."), true);
		

		server.setHandler(handlers);
		server.start();
		server.join();

	}

	public static void addDocRoot(String path, File directory, boolean allowDirListing)
			throws Exception {

		ServletHandler sh = new ServletHandler();

		ServletHolder holder = new ServletHolder(DefaultServlet.class);

		holder.setInitParameter("resourceBase", directory.getAbsolutePath());
		holder
				.setInitParameter("dirAllowed", Boolean
						.toString(allowDirListing));

		sh.addServletWithMapping(holder, "/");

		// Sec ->
		Constraint constraint = new Constraint();
		constraint.setName(Constraint.__BASIC_AUTH);
		constraint.setRoles(new String[] { "user" });
		constraint.setAuthenticate(true);

		ConstraintMapping cm = new ConstraintMapping();
		cm.setConstraint(constraint);
		cm.setPathSpec("/*");

		SecurityHandler sech = new SecurityHandler();
		sech.setUserRealm(new HashUserRealm("MyRealm", "realm.properties"));
		sech.setConstraintMappings(new ConstraintMapping[] { cm });

		// <- Sec
		
		ContextHandler context = new ContextHandler();
		context.addHandler(sh);
		context.addHandler(sech);

		context.setContextPath(path);
		handlers.addHandler(context);
		context.start();
	}

}
