/**
 * Copyright 1986-2007 Alexey Kuznetsov
 *
 * All rights reserved. 
 */

package com.swsoft.trial.server;

import java.io.File;
import java.io.IOException;

import com.swsoft.trial.common.ThreadSafe;

/**
 * This is a server class
 * 
 * @author Kuznetsov A.
 * @version 1.0
 */
@ThreadSafe
public class Core {
	
	public Core(String configFileName) throws IOException {
		this.configurator = new Configurator(new File(configFileName));
	}
	
	public synchronized void start() {
		if (coreThread==null || !coreThread.isAlive()) {
			coreThread = new CoreThread(configurator);
			coreThread.start();
		}
	}
	
	public synchronized void stop() {
		if (coreThread!=null && coreThread.isAlive()) {
			coreThread.stopThread();
		}
	}
	
	public boolean join() {
		
		if (coreThread!=null && coreThread.isAlive()) {
			try { 
				coreThread.join();
				return true;
			} catch(InterruptedException e) { }
		}		
		return false;
	}
	
	private final Configurator configurator;
	private ManagedThread coreThread;

}
