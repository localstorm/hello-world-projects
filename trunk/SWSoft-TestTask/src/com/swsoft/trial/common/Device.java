/**
 * Copyright 1986-2007 Alexey Kuznetsov
 *
 * All rights reserved. 
 */

package com.swsoft.trial.common;

/**
 * Pair {device-name, device-file-system} class
 * @author Kuznetsov A.
 */
@ThreadSafe
public final class Device {

	public Device(String name, String fs) {
		this.name = name;
		this.fileSystem = fs;
	}
	
	public String getFileSystem() {
		return fileSystem;
	}
	
	public String getName() {
		return name;
	}
	
	private final String name;
	private final String fileSystem;
}
