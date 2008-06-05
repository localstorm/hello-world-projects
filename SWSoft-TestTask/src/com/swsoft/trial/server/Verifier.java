/**
 * Copyright 1986-2007 Alexey Kuznetsov
 *
 * All rights reserved. 
 */

package com.swsoft.trial.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.swsoft.trial.common.Device;

/**
 * This class checks if all client's mounted devices have allowed filesystems
 * @author Kuznetsov A.
 * @version 1.0
 */
public class Verifier {


	/**
	 * This routine returns collection of devices that have odd filesystems or <code>null</code> if
	 * client's host is not registered  
	 * @param host Client's host descriptor
	 * @param conf Server's configurator instance
	 * @return collection of devices that have odd filesystems or <code>null</code> if client's host is not registered
	 */
	public static Collection<Device> getIllegalDevices(Host host, Configurator conf) {

		String address = host.getRequestAddress().getHostAddress();

		if (conf.isHostRegistered(address)) {
			Collection<Device> devices  = host.getDevices();
			Collection<Device> illegal  = new ArrayList<Device>(devices.size());
			Collection<String> allowedFS = conf.getAllovedFileSystems(address);
			
			for (Device d: devices) {
				if (!allowedFS.contains(d.getFileSystem())) {
					illegal.add(d);
				}
			}
			
			return Collections.unmodifiableCollection(illegal);
		} else {
			return null;
		}
	}
	
}
