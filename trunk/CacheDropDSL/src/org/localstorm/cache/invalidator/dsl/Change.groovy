/**
 * Copyright 1986-2009 Alexey Kuznetsov
 *
 * All rights reserved. 
 */

package org.localstorm.cache.invalidator.dsl

import java.util.Arrays;

public class Change {
	Resource res;
	String[] args;
	
	Change(Resource res, String[] args){
		this.res = res;
		this.args = args;
	}

	void triggers(Closure c) {
		StaticConstruction.newRule(this);
		c.call();
	}

	String toString() {
		return "change "+res.toString()+"( "+Arrays.asList(args) +" )"
	}
}