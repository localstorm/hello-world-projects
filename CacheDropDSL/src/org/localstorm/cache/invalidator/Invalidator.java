/**
 * Copyright 1986-2009 Alexey Kuznetsov
 *
 * All rights reserved. 
 */

package org.localstorm.cache.invalidator;

import java.util.Map;

public interface Invalidator {
	void invalidate(String name, Map<String, String> params);
}
