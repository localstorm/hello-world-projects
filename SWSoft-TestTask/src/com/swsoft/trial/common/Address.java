/**
 * Copyright 1986-2007 Alexey Kuznetsov
 *
 * All rights reserved. 
 */

package com.swsoft.trial.common;

/**
 * Pair {host-name, host-address} class
 * @author Kuznetsov A.
 */
@ThreadSafe
public final class Address {
	
	public Address(String ip, String name){
		this.ip = ip;
		this.name = name;
	}
	
	public String getHostAddress(){
		return ip;
	}
	
	public String getHostName(){
		return name;
	}
	
	private final String ip;
	private final String name;
	
}
