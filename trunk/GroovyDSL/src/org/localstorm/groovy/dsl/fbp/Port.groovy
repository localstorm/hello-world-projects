package org.localstorm.groovy.dsl.fbp

class Port {
    def name
    def parent
    
    Port(parent, name) {
	this.parent = parent
	this.name = name
    }
    
    Port rightShift(Port port) {
	println("Connecting "+parent+":"+name+" -> "+port.parent+":"+port.name)    
	return this
    }    

}