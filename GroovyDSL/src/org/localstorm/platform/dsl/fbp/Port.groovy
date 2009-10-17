package org.localstorm.platform.dsl.fbp

import org.localstorm.platform.*;

class Port {
	PortInternal internal;
	String name
	def parent
	
	Port(PortInternal pi) {
		if (pi==null) {
			throw new NullPointerException("Given PortInternal is null!");
		}
		
		this.internal = pi
		this.parent = pi.parent
		this.name = pi.name
	}
	
	Port rightShift(Port port) {
		println "Connecting "+parent+":"+name+" -> "+port.parent+":"+port.name    
		return this
	}

	Port leftShift(Port port) {
		println "Connecting "+port.parent+":"+port.name+"-> "+parent+":"+name    
		return port
	}    

}