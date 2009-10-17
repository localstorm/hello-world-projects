package org.localstorm.platform.dsl.fbp

class Boundary {
	static Stack<Component> stack = new Stack<Component>();
	
	static void pushComponent(Component c) {
		stack.push(c)
	}
	
	static Component popComponent() {
		stack.pop()
	}
	
	static Port port(String name) {
		if (stack.size()==0) {
			throw new IllegalStateException("Illegal call context for Boundary.port()");
		}
		
		Component c = stack.peek();
		return c?.port(name);
	}
	
} 