package org.localstorm.groovy.dsl.fbp

class DSL {
    
    static void init() {
	println("Initializing DSL...")
		
        String.metaClass.component = {
    	    props -> String name = delegate
	             println "Creating or looking up component '"+name+"'"
	             return new Component(name, props)
        }
    }
            
}
            