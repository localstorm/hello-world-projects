package org.localstorm.platform;

import java.util.Map;

public interface ComponentFactory {	
	ComponentInternal instantiate(Map<String, Object> props);
}
