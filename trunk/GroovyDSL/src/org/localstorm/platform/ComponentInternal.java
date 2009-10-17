package org.localstorm.platform;

import java.util.Map;

public interface ComponentInternal {
	String getName();
	PortInternal getOrCreatePort(String name);
	
	void setProperties(Map<String, Object> props);
	Object getProperty(String name);
}
