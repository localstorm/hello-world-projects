package org.localstorm.platform.dsl.fbp

import org.localstorm.platform.*;

// This class doesn't represent actual component. It
// is a part of DSL component stub to make it easier to build
// application model

class Component {
	ComponentInternal internal;

	Component(ComponentInternal ci)	{
		internal = ci
	}

	String getName() {
		internal.getName()
	}

	static void declarations(Map<String, Object> declMap) {
		if (declMap) {
			for (entry in declMap.entrySet()) {
				println "Processing '"+entry.key+"' declaration..."
				def definition = entry.value
				String name = entry.key
				
				if (definition instanceof Class) {
					tryCompositeScriptDefinition(name, definition);
					continue;
				}
				if (definition instanceof ComponentFactory) {
					ComponentFactories.instance.register(entry.key, definition);
					continue;
				}

				throw new UnknownComponentDeclarationException(name, definition);
			}
		}
	}
	
	Port port(name) {
		println "Creating or looking up port '"+this.name+":"+name+"'"
		return new Port(internal.getOrCreatePort(name))
	}
	
	String toString() {
		return name
	}
	
	def propertyMissing(String name, value) { 
		println "Setting property '"+name+"' = ["+value+"] for component "+this.name
		internal.setProperties([name: value]);
	}
	
	def propertyMissing(String name) { 
		println "Getting property '"+name+"'for component "+this.name
		return internal.getProperty(name);
	}
	
	private static tryCompositeScriptDefinition(final String _name, final Class<?> definition) {
		final ScriptBasedComponent sbc = new ScriptBasedComponent(_name);
		
		ComponentFactories.instance.register(_name, new ComponentFactory() {
			public ComponentInternal instantiate(String name, Map<String,Object> props) {
				Boundary.pushComponent(sbc)
				sbc.setProperties(props);
				sbc.setCreationAllowed(true);
					definition.main([name] as String[]);
				sbc.setCreationAllowed(false);
				Boundary.popComponent()
				return sbc; 
			}
		});
	}

}