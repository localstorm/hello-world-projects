/**
 * Copyright 1986-2009 Alexey Kuznetsov
 *
 * All rights reserved. 
 */
 
package org.localstorm.cache.invalidator.dsl

public class Resource {
	final String name

	Resource(String name){
		this.name = name
	}

	String toString() {
		return name
	}
}