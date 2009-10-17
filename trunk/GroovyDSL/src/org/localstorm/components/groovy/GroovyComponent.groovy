package org.localstorm.components.groovy

import org.localstorm.platform.ComponentInternal;
import org.localstorm.platform.PortInternal;

public class GroovyComponent implements ComponentInternal {

	public static final String NAME = "GroovyComponent";
	private Map<String, PortInternal> ports;
	private String name;

	public GroovyComponent(String name) {
		ports = new HashMap()
		this.name = name
	}
	
	public void setProperties(Map<String, Object> props) {
		println NAME+" properties: "+props
	}
	
	public Object getProperty(String name) {
		return null
	}
	
	public String getName() {
		return name
	}
	
	public PortInternal getOrCreatePort(String name) {
		PortInternal port = ports.get(name)
		if (port) {
			port = new PortInternal(name, this)
			ports.put(name, port)
		}
		return port
	}
	
	public String toString() {
		return name
	}
}