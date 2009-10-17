package org.localstorm.components.java;

import java.util.HashMap;
import java.util.Map;

import org.localstorm.platform.ComponentInternal;
import org.localstorm.platform.PortInternal;

public class JmsAdapter implements ComponentInternal {
	
	public static final String NAME = "JmsAdapter"; 
	private Map<String, PortInternal> ports;
	private String name;
	
	public JmsAdapter(String name) {
		ports = new HashMap<String, PortInternal>();
		this.name = name;
	}
	
	@Override
	public void setProperties(Map<String, Object> props) {
		
	}

	@Override
	public String getName() {
		return name;
	}
	

	// Just a stub. In real life, port creation can be disabled and
	// only standard precreated ports would be available
	@Override
	public PortInternal getOrCreatePort(String name) {
		PortInternal port = ports.get(name);
		if (port==null) {
			port = new PortInternal(name, this);
			ports.put(name, port);
		}
		return port;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
