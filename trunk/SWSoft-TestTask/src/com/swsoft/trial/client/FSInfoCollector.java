/**
 * Copyright 1986-2007 Alexey Kuznetsov
 *
 * All rights reserved. 
 */

package com.swsoft.trial.client;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

import com.swsoft.trial.common.Device;
import com.swsoft.trial.common.ThreadSafe;

/**
 * <b>GNU/Linux Specific!</b> This class provides file systems info
 *  
 * @author Kuznetsov A.
 * @version 1.0
 */
@ThreadSafe
public class FSInfoCollector {

	/**
	 * Returns <code>Collection<String></code> of supported file systems.
	 * @param pseudo If <code>true</code> pseudo file systems are to be included 
	 * @return <code>Collection<String></code> of supported file systems.
	 * @throws IOException If I/O error occured 
	 */
	public static Collection<String> getSupportedFileSystems(boolean pseudo) throws IOException {
		Collection<String> result = new LinkedList<String>();
		
		InputStream is = new FileInputStream(FILESYSTEMS_PROC_FILE);
		LineIterator it = IOUtils.lineIterator(new InputStreamReader(is));

		try{

			while (it.hasNext()){
				String[] splt = it.nextLine().trim().split(FILESYSTEMS_DELIMITER);
				
				switch(splt.length){
					case 1:
							result.add(splt[0]);
							break;
					case 2:
							if (pseudo){
								result.add(splt[1]);							
							}
							break;
					default:
						throw new IOException(FILESYSTEMS_PROC_FILE + " format has changed!");
				}
			}
			
			it.close();
			return result;
			
		}finally{
			LineIterator.closeQuietly(it);
		}
	}

	/**
	 * Returns mounted devices with file systems in given collection 
	 * @param fs Collection of file system names
	 * @return Mounted devices with file systems in given collection
	 * @throws IOException If I/O error occured
	 */
	public static Collection<Device> getMountedDevices(Collection<String> fs) throws IOException{
		
		Collection<Device> result = new LinkedList<Device>();
		
		if (fs.isEmpty()){
			return result;
		}
		
		InputStream is = new FileInputStream(MOUNTS_PROC_FILE);
		LineIterator it = IOUtils.lineIterator(new InputStreamReader(is));

		try{

			while (it.hasNext()){
				String[] splt = it.nextLine().trim().split(MOUNTS_DELIMITER);
				
				if (splt.length<3){
					throw new IOException(MOUNTS_PROC_FILE + " format has changed!");
				}
				
				String devName = splt[0];
				String devFS = splt[2];
				
				if (fs.contains(devFS)){
					result.add(new Device(devName, devFS));
				}
			}
			
			it.close();
			return result;
			
		}finally{
			LineIterator.closeQuietly(it);
		}
	}
	
	private final static String FILESYSTEMS_PROC_FILE = "/proc/filesystems";
	private final static String FILESYSTEMS_DELIMITER = "\t";
	
	private final static String MOUNTS_PROC_FILE = "/proc/mounts";
	private final static String MOUNTS_DELIMITER = " ";
	
}
