package org.localstorm.platform;

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ComponentFactories {
	private static final ComponentFactories instance = new ComponentFactories();
	
	private ConcurrentNavigableMap<String, ComponentFactory> factories;
	
	private ComponentFactories() {
		factories = new ConcurrentSkipListMap<String, ComponentFactory>();
	}
	
	public static ComponentFactories getInstance() {
		return instance;
	}
	
	public ComponentFactory lookup(String componentName) {
		return factories.get(componentName);
	}
}
