/**
 * Copyright 1986-2007 Alexey Kuznetsov
 *
 * All rights reserved. 
 */

package com.swsoft.trial.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.swsoft.trial.common.Device;
import com.swsoft.trial.util.SocketUtils;

/**
 * <code>PooledThread</code> is used to handle client's requests. 
 * It can't be replaced by standard <code>Thread</code> because it
 * caches <code>Request</code> and <code>Response</code> objects to
 * reuse them in future.
 *  
 * @author Kuznetsov A.
 * @version 1.0
 */
class PooledThread extends ManagedThread {
	
	/**
	 * Creates new <code>PooledThread</code>
	 * @param conf Server's configurator object
	 * @param queue Thread safe <code>Socket</code> queue.
	 * @param logger Server's logger.
	 */
	public PooledThread(Configurator conf, BlockingQueue<Socket> queue, Logger logger){
		this.queue 	= queue;
		this.logger = logger;
		this.conf 	= conf;
		
		try{
			response = new Response();
			request  = new Request(); 
		} catch(IOException e) {
			logger.error(e);
		}
	}
	
	public void run() {
		while (keepRunning()) {
			try {
				
				Socket sock = queue.poll(POLL_TIMEOUT, TimeUnit.MILLISECONDS);
				if (sock!=null) {
					handle(sock);
				}
				
			} catch(Throwable e) {
				logger.error(e);
			}
		}
	}
	
	/**
	 * Ths is a clien't handling routine
	 * @param sock Client's <code>Socket</code>
	 */
	protected void handle(Socket sock) {
		InputStream 	is = null;
		OutputStream 	os = null;
		
		try {
			
			sock.setSoTimeout(CLIENT_TIMEOUT);
			
			is = sock.getInputStream();
			os = sock.getOutputStream();
			
			Host host = request.getRequestInformation(is, conf.getMaxRequestSize());
			host.setRequestAddress(sock.getInetAddress());
			
			Collection<Device> illegal = Verifier.getIllegalDevices(host, conf);
			
			if (illegal!=null){
				response.writeResponse(illegal, os);
			} else{
				response.writeUnknownHostResponse(os);
			}

			is.close();
			os.close();
			sock.close();
			
		} catch(IOException e){
			e.printStackTrace();
			logger.error("I/O error occured during request handling.");
			logger.error(e);
		} finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(os);
			SocketUtils.closeQuietly(sock);
		}
	}
	
	protected final Logger logger;
	protected final Configurator conf;
	
	protected Response response;
	protected Request request;
	
	protected final BlockingQueue<Socket> queue;
	
	private final static long POLL_TIMEOUT = 250;
	private final static int CLIENT_TIMEOUT = 10000;
}
