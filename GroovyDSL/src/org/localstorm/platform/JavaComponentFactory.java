package org.localstorm.platform;

import java.lang.reflect.Constructor;
import java.util.Map;

public class JavaComponentFactory implements ComponentFactory {

	private Class<? extends ComponentInternal> fClass;
	
	public JavaComponentFactory(Class<? extends ComponentInternal> c) {
		this.fClass = c;
	}
	
	@Override
	public ComponentInternal instantiate(String name, Map<String, Object> props) {
		try {
			Constructor<? extends ComponentInternal> c = fClass.getConstructor(String.class);
			ComponentInternal ci = c.newInstance(name);
			ci.setProperties(props);
			return ci;
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

}
