package org.localstorm.groovy.dsl.fbp

// This class doesn't represent actual component. It
// is a part of DSL component stub to make it easier to build
// application model

class Component {
    def name
    
    Component(name, Map properties) {
	this.name = name
	if (properties) {
    	    for (entry in properties.entrySet()) {
    		println "Setting property '"+entry.key+"' = ["+entry.value+"] for component "+name
	    }
	}
    }
    
    Port port(name) {
	println "Creating or looking up port '"+this.name+":"+name+"'"
	return new Port(this, name)
    }
    
    String toString() {
	return name
    }
    
    def propertyMissing(String name, value) { 
	println "Setting property '"+name+"' = ["+value+"] for component "+this.name
    }

   def propertyMissing(String name) { 
	println "Getting property '"+name+"'for component "+this.name
   }
       
    
}