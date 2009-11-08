/**
 * Copyright 1986-2009 Alexey Kuznetsov
 *
 * All rights reserved. 
 */

package org.localstorm.cache.invalidator.dsl

public class DSL {
	static void init() {
	    println "Initializing DSL..."
	
	    String.metaClass.getResource = { ->	return new Resource(delegate) }
	    StaticConstruction.clear();
	}

	static Change changeOf(Resource res, String... args) { return new Change(res, args) }

	static void drop(String cacheName, Map<String, ParamReference> references) {
		Rule r = StaticConstruction.getCurrentRule()
		if (r == null) {
			throw new IllegalStateException("Can't determine current rule. 'drop' statement should be used inside 'triggers' clause")
		}

		r.newDrop(new DropAction(cacheName, references))
	}

	static void drop(Map<String, ParamReference> references, String cacheName) {
		drop(cacheName, references);
	}

	static void causes(Change change, Map<String, ParamReference> references) {
		Rule r = StaticConstruction.getCurrentRule()
		if (r == null) {
			throw new IllegalStateException("Can't determine current rule. 'cause' statement should be used inside 'triggers' clause")
		}

		r.newCause(new CauseAction(change, references));
	}

	static void causes(Map<String, String> references, Change change) {
		causes(change, references);
	}
}