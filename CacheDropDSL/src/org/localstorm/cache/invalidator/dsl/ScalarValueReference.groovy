/**
 * Copyright 1986-2007 Alexey Kuznetsov
 *
 * All rights reserved. 
 */

package org.localstorm.cache.invalidator.dsl;

public class ScalarValueReference extends ParamReference {

	String value
	
	public ScalarValueReference(String value) {
		super(value)
		this.value = value
	}

	public String getParamValue(Map<String,String> scope) {
		return value
	}
}