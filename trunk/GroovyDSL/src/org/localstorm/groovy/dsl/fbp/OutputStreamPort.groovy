package org.localstorm.groovy.dsl.fbp

import java.io.OutputStream

class OutputStreamPort extends Port {
    def OutputStream os;
    
    OutputStreamPort(parent, name, os) {
	super(parent, name)
	this.os = os;
    }

    String toString() {
	return parent.name+":"+name
    }
}