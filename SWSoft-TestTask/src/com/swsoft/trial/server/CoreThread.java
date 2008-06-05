/**
 * Copyright 1986-2007 Alexey Kuznetsov
 *
 * All rights reserved. 
 */

package com.swsoft.trial.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import com.swsoft.trial.util.SocketUtils;
import org.apache.log4j.Logger;

class CoreThread extends ManagedThread {

	public CoreThread(Configurator conf) {
		this.logger = LogHelper.getLogger();
		this.conf = conf;
		this.pool = new ThreadPool(logger, conf);
	}
	
	@Override
	public void run() {
		
		int port 	= conf.getPort();
		ServerSocket s = null;
		
		try {
			
			logger.info("Opening server socket on port "+port);
			s = new ServerSocket(port);
			s.setSoTimeout(ACCEPT_TIMEOUT);
			logger.info("Server socket on port ready.");

			logger.debug("Starting thread pool.");
			pool.start();
			logger.debug("Thread pool ready.");
		
			while (keepRunning()) {
				Socket sock = null;
				try {
					sock = s.accept();
					pool.handle(sock);
				} catch(SocketTimeoutException e) {
					continue;
				} catch(InterruptedException e){
					// Queue was blocked and someone had interrupted us
					SocketUtils.closeQuietly(sock);
					break;
				} catch(IOException e) {
					logger.error(e);
				}
			}
			
			s.close();
		
		} catch(IOException e) {
			logger.fatal(e);
		}finally{
			SocketUtils.closeQuietly(s);
			
			logger.debug("Stopping thread pool.");
			pool.stop();
			pool.join();
			logger.debug("Thread pool stopped.");
			
			logger.debug("Dropping queued connections.");
			pool.dropQueuedSockets();
			logger.debug("Queued connections dropped.");
		}
		
	}
	
	private final ThreadPool pool;
	private final Configurator conf;
	private final Logger logger;
	
	private final static int ACCEPT_TIMEOUT = 250;
}
