package org.localstorm.platform;

import java.util.Map;

public interface ComponentFactory {	
	ComponentInternal instantiate(String name, Map<String, Object> props);
}
