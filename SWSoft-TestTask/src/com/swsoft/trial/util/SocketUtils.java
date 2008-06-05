/**
 * Copyright 1986-2007 Alexey Kuznetsov
 *
 * All rights reserved. 
 */

package com.swsoft.trial.util;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class provides additional functionality to operate with sockets
 * @author Kuznetsov A.
 * @version 1.0
 */
public class SocketUtils {

	/**
	 <code>
	 if (sock!=null){
		try{
			sock.close();
		}catch(Throwable e){
		}
	 }
	 </code>	
	 * @param sock Given socket
	 */
	public static void closeQuietly(Socket sock){
		if (sock!=null){
			try{
				sock.close();
			}catch(Throwable e){
			}
		}
	}
	
	/**
	 <code>
	 if (sock!=null){
		try{
			sock.close();
		}catch(Throwable e){
		}
	 }
	 </code>	
	 * @param sock Given socket
	 */
	public static void closeQuietly(ServerSocket sock){
		if (sock!=null){
			try{
				sock.close();
			}catch(Throwable e){
			}
		}
	}
	
	private SocketUtils() {
	}
	
}
