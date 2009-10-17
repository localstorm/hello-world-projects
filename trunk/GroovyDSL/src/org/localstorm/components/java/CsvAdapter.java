package org.localstorm.components.java;

import java.util.HashMap;
import java.util.Map;

import org.localstorm.platform.ComponentBase;
import org.localstorm.platform.PortInternal;

public class CsvAdapter extends ComponentBase {
	
	public static final String NAME = "CsvAdapter";
	private Map<String, PortInternal> ports;

	public CsvAdapter(String name) {
		super(name);
		ports = new HashMap<String, PortInternal>();
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
	

}
