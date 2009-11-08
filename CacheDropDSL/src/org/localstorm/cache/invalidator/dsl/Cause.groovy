/**
 * Copyright 1986-2009 Alexey Kuznetsov
 *
 * All rights reserved. 
 */
package org.localstorm.cache.invalidator.dsl

public class Cause {

	static Cause instance = new Cause()

	private Cause(){}
	
	def propertyMissing(String name) {
		return new ParamReference(name);
	}
}