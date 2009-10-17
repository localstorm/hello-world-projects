package org.localstorm.platform;

import java.util.HashMap;
import java.util.Map;

public class ScriptBasedComponent implements ComponentInternal {

	private final String name;
	private Map<String, PortInternal> ports;
	private boolean creationAllowed;
	
	public ScriptBasedComponent(String name) {
		this.name = name;
		ports = new HashMap<String, PortInternal>();
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setProperties(Map<String, Object> props) {
		
	}
	
	public void setCreationAllowed(boolean creationAllowed) {
		this.creationAllowed = creationAllowed;
	}
	
	public boolean isCreationAllowed() {
		return creationAllowed;
	}
	
	@Override
	public PortInternal getOrCreatePort(String name) {
		PortInternal port = ports.get(name);
		if (port==null) {
			if (creationAllowed){
				port = new PortInternal(name, this);
				ports.put(name, port);
			} else {
				throw new RuntimeException("Port not found!");
			}
		}
		return port;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
