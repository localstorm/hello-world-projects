package org.localstorm.platform.dsl.fbp

import org.localstorm.platform.*;

class Boundary {
	static Stack<ScriptBasedComponent> stack = new Stack<ScriptBasedComponent>();
	
	static void pushComponent(ScriptBasedComponent c) {
		stack.push(c)
	}
	
	static ScriptBasedComponent popComponent() {
		stack.pop()
	}
	
	static Port port(String name) {
		if (stack.size()==0) {
			throw new IllegalStateException("Illegal call context for Boundary.port()");
		}
		
		ScriptBasedComponent c = stack.peek();
		return new Port(c.getOrCreatePort(name));
	}
	
} 