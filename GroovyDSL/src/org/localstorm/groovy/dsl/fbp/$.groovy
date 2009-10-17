package org.localstorm.groovy.dsl.fbp

class $ {
	
	private static _self = new Component("\$", [:])
	
	static Port out = new OutputStreamPort(_self, "out", System.out)
	static Port err = new OutputStreamPort(_self, "err", System.err)
	
	private $() {
	}
}