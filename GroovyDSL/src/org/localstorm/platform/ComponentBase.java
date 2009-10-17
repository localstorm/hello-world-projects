package org.localstorm.platform;

import java.util.LinkedHashMap;
import java.util.Map;


public abstract class ComponentBase implements ComponentInternal {

	private final String name;
	private final Map<String, Object> properties;
	
	public ComponentBase(String name) {
		this.name = name;
		this.properties = new LinkedHashMap<String, Object>();
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public Object getProperty(String name) {
		return properties.get(name);
	}

	@Override
	public void setProperties(Map<String, Object> props) {
		if (props!=null) {
			for (Map.Entry<String, Object> e: props.entrySet()) {
				properties.put(e.getKey(), e.getValue());
			}
		}
	}

	@Override
	public void setProperty(String name, Object value) {
		properties.put(name, value);
	}
	
	@Override
	public String toString() {
		return name;
	}

}
