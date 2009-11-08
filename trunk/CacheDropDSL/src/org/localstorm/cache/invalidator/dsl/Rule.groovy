/**
 * Copyright 1986-2009 Alexey Kuznetsov
 *
 * All rights reserved. 
 */

package org.localstorm.cache.invalidator.dsl;

public class Rule {
	Change trigger
	List<DropAction> drops
	List<CauseAction> causes
	
	Rule(Change c){
		this.trigger = c
		this.drops = new LinkedList()
		this.causes = new LinkedList()
	}

	void newDrop(DropAction drop) {
		drops.add(drop)
	}

	void newCause(CauseAction cause) {
		causes.add(cause)
	}
}