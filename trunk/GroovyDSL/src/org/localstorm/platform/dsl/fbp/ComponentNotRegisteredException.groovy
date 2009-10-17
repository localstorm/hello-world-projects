package org.localstorm.platform.dsl.fbp

public class ComponentNotRegisteredException extends Exception {
	public ComponentNotRegisteredException(String name) {
		super("Component factory for component ["+name+"] not found");
	}
}