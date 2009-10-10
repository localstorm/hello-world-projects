package org.localstorm.groovy.dsl.fbp

class Components {
    
    Components(Map declMap) {
	if (declMap) {
	    for (entry in declMap.entrySet()) {
		println "Processing '"+entry.key+"' declaration..."

		// TODO: invoke entry.value.main() method here
		
    		println "Component declaration: '"+entry.key+"' is defined in ["+entry.value+"]"
    		
    	    }
        }
    }

    def propertyMissing(String name, value) { 
	println "Component '"+name+"' is defined by ["+value+"]"
    }
    
}