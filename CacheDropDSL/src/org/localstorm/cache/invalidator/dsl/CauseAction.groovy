/**
 * Copyright 1986-2007 Alexey Kuznetsov
 *
 * All rights reserved. 
 */

package org.localstorm.cache.invalidator.dsl;

public class CauseAction {

	Change change
	Map<String, ParamReference> refs
	
	public CauseAction(Change c, Map<String, ParamReference> refs) {
		change = c
		this.refs = refs 
	}

	String toString() {
		return "causes "+change.toString() + " { " + refs + " }"
	}
}