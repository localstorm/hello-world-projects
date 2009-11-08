/**
 * Copyright 1986-2009 Alexey Kuznetsov
 *
 * All rights reserved. 
 */

package org.localstorm.cache.invalidator;

import java.util.Map;

public interface CacheInvalidator {
	public void handleResourceChange(String resourceName, Map<String, String> attributes);
}
