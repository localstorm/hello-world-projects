package org.localstorm.platform.dsl.fbp

public class UnknownComponentDeclarationException extends Exception {

	public UnknownComponentDeclarationException(String name, Object definition) {
		super("Unknown component declaration: "+name+"->"+definition);
	}
}