package org.localstorm.platform;

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import org.localstorm.components.java.CsvAdapter;
import org.localstorm.components.java.JmsAdapter;

public class ComponentFactories {
	private static final ComponentFactories instance = new ComponentFactories();
	
	private ConcurrentNavigableMap<String, ComponentFactory> factories;
	
	private ComponentFactories() {
		factories = new ConcurrentSkipListMap<String, ComponentFactory>();
		
		// Adding standard components (in real system this should be done using class-loading)
		factories.put(CsvAdapter.NAME, new JavaComponentFactory(CsvAdapter.class));
		factories.put(JmsAdapter.NAME, new JavaComponentFactory(JmsAdapter.class));
	}
	
	public static ComponentFactories getInstance() {
		return instance;
	}
	
	public ComponentFactory lookup(String componentName) {
		return factories.get(componentName);
	}
	
	public void register(String name, ComponentFactory definition) {
		factories.put(name, definition);
	}
}
