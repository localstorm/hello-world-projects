package org.localstorm.components.java;

import java.util.HashMap;
import java.util.Map;

import org.localstorm.platform.ComponentBase;
import org.localstorm.platform.PortInternal;

public class JmsAdapter extends ComponentBase {
	
	public static final String NAME = "JmsAdapter"; 
	private Map<String, PortInternal> ports;
	
	public JmsAdapter(String name) {
		super(name);
		ports = new HashMap<String, PortInternal>();
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
}
