/**
 * Copyright 1986-2007 Alexey Kuznetsov
 *
 * All rights reserved. 
 */

package com.swsoft.trial.server;

import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

import com.swsoft.trial.common.ThreadUnsafe;
import com.swsoft.trial.util.SocketUtils;


/**
 * This is a special thread pool. It contains <code>Socket</code> queue
 * and a set of consumer <code>PooledThread</code> instances. Instances
 * are thread unsafe, you should not use them from concurrent theads
 */
@ThreadUnsafe
class ThreadPool {
	
	/**
	 * Creates new <code>ThreadPool</code> instance.
	 * @param logger Server's logger 
	 * @param conf Server's configurator instance
	 */
	public ThreadPool(Logger logger, Configurator conf) {
		
		int poolSize = conf.getPoolSize();
		this.pool 	= new ArrayBlockingQueue<PooledThread>(poolSize);
		this.queue 	=  new ArrayBlockingQueue<Socket>(conf.getQueueSize());
		this.threads = new PooledThread[poolSize];
		
		for (int i=0; i<poolSize; i++) {
			threads[i] = new PooledThread(conf, queue, logger);
			pool.add(threads[i]);
		}
		
	}
	
	/**
	 * Adds given socket to queue
	 * @param sock Client's socket
	 * @throws InterruptedException If queue was full and thread was interrupted
	 */
	public void handle(Socket sock) throws InterruptedException {
		queue.put(sock);
	}

	/**
	 * Returns number of consumer threads in pool
	 * @return number of consumer threads in pool
	 */
	public int getSize() {
		return threads.length;
	}
	
	
	/**
	 * Starts consumer threads is pool
	 */
	public void start() {
		for (PooledThread thread: threads) {
			thread.start();
		}
	}

	/**
	 * Stops consumer thread in pool. If handle routine in <code>PooledThread</code> 
	 * is not infinite this method will succeed. This is non-blocking method.
	 */
	public void stop() {
		for (PooledThread thread: threads) {
			thread.stopThread();
		}
	}
	
	/**
	 * Waits untill all pooled threads are stopped.
	 * @return <code>true</code> is all threads are stopped, <code>false</code> is <code>InterruptedException</code> raised
	 */
	public boolean join() {
		try {
			for (PooledThread thread: threads) { 
				thread.join();
			}
			return true;
		} catch(InterruptedException e) {
			return false;
		}
	}

	/**
	 * Quietly closes remaining sockets in queue.
	 */
	public void dropQueuedSockets() {
		while (!queue.isEmpty()) {
			SocketUtils.closeQuietly(queue.remove());
		}
	}
	
	private final BlockingQueue<Socket> queue;
	private final BlockingQueue<PooledThread> pool;
	private final PooledThread[] threads;
}
