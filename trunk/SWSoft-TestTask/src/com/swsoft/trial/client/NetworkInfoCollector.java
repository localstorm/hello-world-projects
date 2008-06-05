/**
 * Copyright 1986-2007 Alexey Kuznetsov
 *
 * All rights reserved. 
 */

package com.swsoft.trial.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedList;

import com.swsoft.trial.common.Address;

/**
 * This utility class retrieves all network <code>InetAddress</code> addresses of current computer
 * All newtwork interfaces are to be checked, you may skip loopback interfaces
 * 
 * @author Kuznetsov A.
 * @version 1.0
 */
public class NetworkInfoCollector {
	
	/**
	 * Returns local newtwork addresses
	 * @param loop If loopback interfaces are to be checked
	 * @return Local newtwork addresses
	 * @throws IOException If I/O error occured
	 */
	public static Collection<Address> getActiveNetworkAddresses(boolean loop) throws IOException {
		
		Collection<Address> res = new LinkedList<Address>();
		Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
		
		while (interfaces.hasMoreElements()) {
			NetworkInterface ifs = interfaces.nextElement();

			Enumeration<InetAddress> ia = ifs.getInetAddresses();
			while (ia.hasMoreElements()){
				InetAddress addr = ia.nextElement();
				if (addr.isLoopbackAddress() && !loop){
					continue;
				}
				// This could be a bottleneck because of DNS lookups
				res.add(new Address(addr.getHostAddress(), addr.getHostName()));
			}
		}
		
		return res;
	}
}
