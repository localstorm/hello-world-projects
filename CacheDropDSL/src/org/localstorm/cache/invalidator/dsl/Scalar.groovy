/**
 * Copyright 1986-2009 Alexey Kuznetsov
 *
 * All rights reserved. 
 */

package org.localstorm.cache.invalidator.dsl

public class Scalar {
	static ScalarValueReference value(def v) {
		return new ScalarValueReference(v.toString())
	}
}