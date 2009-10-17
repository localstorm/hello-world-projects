package org.localstorm.platform.dsl.fbp

import org.localstorm.platform.*

class Boundary {
	static Stack<ScriptBasedComponent> stack = new Stack<ScriptBasedComponent>();

	private Boundary() {
		
	}

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

	static def getBoundaryProperty(String name) {
		if (stack.size()==0) {
			throw new IllegalStateException("Illegal call context for Boundary.getBoundaryProperty()");
		}
		ScriptBasedComponent c = stack.peek();
		println "Getting property '"+name+"' for component "+c.name
		return c.getProperty(name);
	}

	def propertyMissing(String name, value) {
		if (stack.size()==0) {
			throw new IllegalStateException("Illegal call context for Boundary."+name+"="+value);
		}
		println "Setting property '"+name+"' = ["+value+"] for component "+this.name

		ScriptBasedComponent c = stack.peek();
		c.setProperties([name: value]);
	}
	
    def propertyMissing(String name) {
		if (stack.size()==0) {
			throw new IllegalStateException("Illegal call context for Boundary.port()");
		}
		println "Getting property '"+name+"'for component "+c.getName();

		ScriptBasedComponent c = stack.peek();
		return c.getProperty(name);
	}

} 