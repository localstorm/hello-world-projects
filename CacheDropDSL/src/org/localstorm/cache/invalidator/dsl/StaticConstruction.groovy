/**
 * Copyright 1986-2009 Alexey Kuznetsov
 *
 * All rights reserved. 
 */

package org.localstorm.cache.invalidator.dsl

import org.localstorm.cache.invalidator.Invalidator

public class StaticConstruction {
	static Stack<Rule> rules = new Stack()
	static List<Rule> constructedRules = new LinkedList()

	static void clear() {
		rules = new Stack()
		constructedRules = new LinkedList()
	}

	static void newRule(Change c) {
		if (getCurrentRule()) {
			formalizeRule()
			rules.pop()
		}
		
		rules.push(new Rule(c))
	}

	static Rule getCurrentRule() {
		return (rules.isEmpty())? null: rules.peek()
	}

	static ProcessingEngine getEngine(Invalidator inv) {
		while (getCurrentRule()) {
			formalizeRule()
			rules.pop()
		}
	
		return new ProcessingEngine(inv, constructedRules)
	}

	static void formalizeRule() {
		constructedRules.add(getCurrentRule())
	}
}