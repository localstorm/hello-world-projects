package org.localstorm.comet;

import java.io.IOException;
import java.net.BindException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.HttpConnection;
import org.mortbay.jetty.Request;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.ContextHandler;
import org.mortbay.jetty.handler.ContextHandlerCollection;
import org.mortbay.jetty.handler.DefaultHandler;
import org.mortbay.jetty.handler.HandlerCollection;
import org.mortbay.jetty.handler.RequestLogHandler;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.thread.BoundedThreadPool;
import org.mortbay.thread.ThreadPool;

class JettyHttpServer {
	private int m_port;
	private Server m_jettyServer = null;

	private ContextHandlerCollection contextHandlers = new ContextHandlerCollection();

	private int m_maxThreadPoolSize = -1;
	private int m_minThreadPoolSize = -1;

	/**
	 * Construct Jetty Server with WebApps support and specified signal handler.
	 * 
	 * @param docRoot
	 * @param port
	 * @param signalHandler
	 * @throws IOException
	 */
	JettyHttpServer(int port) throws IOException {
		this.m_port = port;
		init(port, true);
		start();
	}

	/**
	 * Initialize the server.
	 * 
	 * @param port
	 * @param mainHandler
	 * @param signalHandler
	 * @param supportWebApps
	 * @throws IOException
	 */
	private void init(int port, boolean supportWebApps) throws IOException {
		try {
			m_jettyServer = new org.mortbay.jetty.Server(port);
			m_jettyServer.setStopAtShutdown(true);

			SelectChannelConnector connector = new SelectChannelConnector();
			connector.setPort(port);
			m_jettyServer.setConnectors(new Connector[] { connector });

			contextHandlers.setHandlers(new ContextHandler[0]);

			// General handlers collection to be given to the server
			HandlerCollection serverHandlers = new HandlerCollection();

			// if no WebApps support
			serverHandlers.setHandlers(new Handler[] { contextHandlers,
					new DefaultHandler(), new RequestLogHandler() });

			m_jettyServer.setHandler(serverHandlers);
		} catch (Exception e) {
			throw new IOException(e.getMessage());
		}
	}

	/**
	 * Register a GenericCallback handler for the specified context path.
	 * 
	 * @param path
	 *            server context path
	 * @param callback
	 *            the callback to register
	 * @throws Exception
	 *             if context couldn't be started
	 */
	public void addCallback(String path, GenericCallback callback)
			throws Exception {
		path = normalizePath(path);

		ContextHandler context = new ContextHandler();
		context.setHandler(new GenericRequestHandler(callback, path));
		context.setContextPath(path);
		contextHandlers.addHandler(context);
		context.start();
	}

	/**
	 * Remove Generic callback at the specified path
	 * 
	 * @param path
	 */
	public void removeCallback(String path) {
		path = normalizePath(path);

		for (Handler handler : contextHandlers.getHandlers()) {
			if (handler instanceof ContextHandler
					&& ((ContextHandler) handler).getContextPath().equals(path)) {
				contextHandlers.removeHandler(handler);

				// stop removed handler
				if (handler.isStarted())
					try {
						handler.stop();
					} catch (Exception e) {
						// ignore exception
					}
			}
		}
	}

	private String normalizePath(String path) {
		// strip '/' from the end of path
		if (path.endsWith("/")) {
			Matcher matcher = Pattern.compile(".*(/+)$").matcher(path);
			if (matcher.groupCount() >= 1) {
				path = path.substring(0, matcher.start(1));
			}
		}
		return path;
	}

	/**
	 * Returns the number of registered callbacks.
	 * 
	 * @return number of callbacks
	 */
	public int getRegisteredCallbacksCount() {
		return contextHandlers.getHandlers().length;
	}

	/**
	 * Returns array of registered callbacks.
	 * 
	 * @return callbacks
	 */
	public GenericCallback[] getRegisteredCallbacks() {
		ArrayList<GenericCallback> callbacks = new ArrayList<GenericCallback>();
		for (Handler requestHandler : contextHandlers
				.getChildHandlersByClass(GenericRequestHandler.class)) {
			callbacks.add(((GenericRequestHandler) requestHandler)
					.getCallback());
		}
		return callbacks.toArray(new GenericCallback[0]);
	}

	/**
	 * Returns array of registered handlers that wrap {@link GenericCallback}.
	 * 
	 * @return array of handlers
	 */
	public GenericRequestHandler[] getGenericRequestHandlers() {
		return Arrays.asList(
				contextHandlers
						.getChildHandlersByClass(GenericRequestHandler.class))
				.toArray(new GenericRequestHandler[0]);
	}

	/**
	 * Returns number of registered {link #GenericCallback} handlers.
	 * 
	 * @return callbacks number
	 */
	public int registeredHandlersCount() {
		return contextHandlers.getHandlers().length;
	}

	/**
	 * Returns the port the server listens to.
	 * 
	 * @return port number
	 */
	public int getPort() {
		return m_port;
	}

	/**
	 * Stop the server.
	 * 
	 * @throws Exception
	 *             on error
	 */
	public void stop() throws Exception {
		if (m_jettyServer != null)
			m_jettyServer.stop();
	}

	/**
	 * Start the http server, so it will accept http requests.
	 * 
	 * @throws IOException
	 */
	public void start() throws IOException {
		try {
			m_jettyServer.start();
		} catch (BindException be) {
			throw (BindException) new BindException(
					"Couldn't start a server on the port " + getPort())
					.initCause(be);
		} catch (Exception e) {
			throw (IOException) new IOException(
					"Couldn't start a server on the port " + getPort())
					.initCause(e);
		}

		_updateThreadPoolSize();

	}

	/**
	 * Set max thread pool size.
	 * 
	 * @param size
	 *            the size of pool
	 */
	public void setMaxThreadPoolSize(int size) {
		m_maxThreadPoolSize = size;
		_updateThreadPoolSize();
	}

	/**
	 * Set minimum thread pool size.
	 * 
	 * @param size
	 *            the size of pool
	 */
	public void setMinThreadPoolSize(int size) {
		m_minThreadPoolSize = size;
		_updateThreadPoolSize();
	}

	/**
	 * Change server pool size but if Jetty server started
	 */
	protected void _updateThreadPoolSize() {
		if (m_jettyServer == null) {
			return;
		}
		ThreadPool threadPool = m_jettyServer.getThreadPool();
		if (threadPool != null) {
			// Jetty server always uses BoundedThreadPool
			if (threadPool instanceof BoundedThreadPool) {
				BoundedThreadPool pool = (BoundedThreadPool) threadPool;
				if (m_maxThreadPoolSize > 0) {
					pool.setMaxThreads(m_maxThreadPoolSize);
				}
				if (m_minThreadPoolSize > 0) {
					pool.setMinThreads(m_minThreadPoolSize);
				}
			} else {
				assert false : "Can't change thread pool size, cause it isn't a BoundedThreadPool";
			}
		}
	}

	/**
	 * GenericCallback wrapper to be installed as http requests handler.
	 */
	public static class GenericRequestHandler extends
			org.mortbay.jetty.handler.AbstractHandler {
		protected GenericCallback callback = null;
		protected String contextPath;

		/**
		 * Construct the handler.
		 * 
		 * @param callback
		 *            the {@link #GenericCallback} to wrap.
		 */
		public GenericRequestHandler(GenericCallback callback,
				String contextPath) {
			if (callback == null) {
				throw new NullPointerException("GenericCallback can't be null");
			}
			this.callback = callback;
			this.contextPath = contextPath;
		}

		/**
		 * Delegate the request handling to internally stored
		 * {@link #GenericCallback}
		 */
		public void handle(String target, HttpServletRequest request,
				HttpServletResponse response, int dispatch) throws IOException,
				ServletException {
			callback.handle(request, response);

			// mark request as handled
			Request base_request = request instanceof Request ? (Request) request
					: HttpConnection.getCurrentConnection().getRequest();
			base_request.setHandled(true);
		}

		/**
		 * Return stored callback.
		 * 
		 * @return callback
		 */
		GenericCallback getCallback() {
			return callback;
		}

		/**
		 * Returns contextPath.
		 * 
		 * @return the contextPath
		 */
		public String getContextPath() {
			return contextPath;
		}
	} // end of GenericRequestHandler

}
