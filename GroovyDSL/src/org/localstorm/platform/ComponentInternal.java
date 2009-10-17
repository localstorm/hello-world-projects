package org.localstorm.platform;

import java.util.Map;

public interface ComponentInternal {
	String getName();
	void setProperties(Map<String, Object> props);
	PortInternal getOrCreatePort(String name);
}
