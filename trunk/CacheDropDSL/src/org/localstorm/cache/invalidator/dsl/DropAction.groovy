/**
 * Copyright 1986-2007 Alexey Kuznetsov
 *
 * All rights reserved. 
 */

package org.localstorm.cache.invalidator.dsl;

import java.util.Map;

public class DropAction {

	String cacheName
	Map<String, ParamReference> refs
	
	public DropAction(String cacheName, Map<String, ParamReference> refs) {
		this.cacheName = cacheName
		this.refs = refs
	}

	String toString() {
		return "drop "+cacheName+"{ "+refs +" }"
	}
}