package org.localstorm.platform.dsl.fbp

import org.localstorm.platform.*;

class DSL {
    
    static void init() {
    	println("Initializing DSL...")
		
        String.metaClass.component = {
    	    props -> String name = delegate
	            println "Looking up component '"+name+"'"
	            ComponentFactories f =  ComponentFactories.instance;
    	    	ComponentFactory factory = f.lookup(name);
    	    	if (!factory) {
    	    		throw new ComponentNotRegisteredException(name);
       	    	}

    	    	ComponentInternal ci = f.instantiate(props);
    	    	return new Component(ci); 
        }

        String.metaClass.getComponent = { -> String name = delegate
	             println "Creating or looking up component '"+name+"'"
	             return new Component(name, [:])
        }
    }
            
}
            