package org.localstorm.components.java;

import java.util.HashMap;
import java.util.Map;

import org.localstorm.platform.ComponentInternal;
import org.localstorm.platform.PortInternal;

public class CsvAdapter implements ComponentInternal {
	
	public static final String NAME = "CsvAdapter";
	private Map<String, PortInternal> ports;
	private String name;

	public CsvAdapter(String name) {
		ports = new HashMap<String, PortInternal>();
		this.name = name;
	}
	
	@Override
	public void setProperties(Map<String, Object> props) {
		System.out.println(NAME+" properties: "+props);
	}
	
	@Override
	public Object getProperty(String name) {
		return null;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
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
