/**
 * Copyright 1986-2007 Alexey Kuznetsov
 *
 * All rights reserved. 
 */

package com.swsoft.trial.server;

import java.net.InetAddress;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import com.swsoft.trial.common.Address;
import com.swsoft.trial.common.Device;
import com.swsoft.trial.common.ThreadUnsafe;

@ThreadUnsafe
final class Host {

	public Host(Collection<Device> devs, Collection<Address> addresses){
		this.addresses = addresses;
		this.devs = devs;
	}
	
	public Host(){
		this.addresses = new LinkedList<Address>();
		this.devs = new LinkedList<Device>();
	}

	public void addDevice(String name, String fs){
		devs.add(new Device(name, fs));
	}
	
	public void addAddress(String host, String ip){
		addresses.add(new Address(ip, host));
	}
	
	public Collection<Device> getDevices(){
		return Collections.unmodifiableCollection(devs);
	}
	
	public Collection<Address> getAddresses(){
		return Collections.unmodifiableCollection(addresses);
	}
	
	public void setRequestAddress(InetAddress address) {
		this.address = address;
	}
	
	public InetAddress getRequestAddress() {
		return address;
	}
	
	private final Collection<Device> devs;
	private final Collection<Address> addresses;
	private InetAddress address;
	
}
