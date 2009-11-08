/**
 * Copyright 1986-2009 Alexey Kuznetsov
 *
 * All rights reserved. 
 */

package org.localstorm.cache.invalidator.dsl

public class ParamReference {

	String ref
	public ParamReference(String ref) {
		this.ref = ref
	}

	String toString() {
		return "@"+ref
	}

	String getParamValue(Map<String, String> scope) {
		return scope.get(ref)
	}
}