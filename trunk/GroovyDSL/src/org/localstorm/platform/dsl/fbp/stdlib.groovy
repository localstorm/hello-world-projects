package org.localstorm.platform.dsl.fbp;

import org.localstorm.platform.*;

public class stdlib {
	static def GroovyComponent = new JavaComponentFactory(org.localstorm.components.groovy.GroovyComponent.class)
	static def CsvAdapter = new JavaComponentFactory(org.localstorm.components.java.CsvAdapter.class)
	static def JmsAdapter = new JavaComponentFactory(org.localstorm.components.java.JmsAdapter.class)
}