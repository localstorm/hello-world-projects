/**
 * Copyright 1986-2009 Alexey Kuznetsov
 *
 * All rights reserved. 
 */

package org.localstorm.cache.invalidator;

import groovy.lang.GroovyClassLoader;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.localstorm.cache.invalidator.dsl.ProcessingEngine;
import org.localstorm.cache.invalidator.dsl.StaticConstruction;

public abstract class ScriptableCacheInvalidator implements CacheInvalidator, Invalidator {

	private final ProcessingEngine engine;
	
	@SuppressWarnings("unchecked")
	public ScriptableCacheInvalidator(String groovyScript) throws Exception {
		String header = "";
		InputStream includeStream = null;
		try {
			includeStream = this.getClass().getResourceAsStream("/org/localstorm/cache/invalidator/dsl/include.dsl");
			header = IOUtils.toString(includeStream);
		} finally {
			IOUtils.closeQuietly(includeStream);
		}
		
		GroovyClassLoader gcl = new GroovyClassLoader();
		
		header = MessageFormat.format(header, "pkg"+System.currentTimeMillis());
		
		Class clazz = gcl.parseClass(header + groovyScript);
		Method main = clazz.getMethod("main", String[].class);
		if (main == null) {
			throw new IllegalArgumentException("Invalid script! static main(String...) method not found!");
		}

		main.invoke(null, (Object) new String[0]);
		engine = StaticConstruction.getEngine(this);
		engine.printRules();
	}
	
	@Override
	public void handleResourceChange(String resourceName,
			Map<String, String> attributes) {
		engine.process(resourceName, attributes);
	}
	
	public abstract void invalidate(String name, Map<String, String> params);
}
