/**
 * Copyright 1986-2007 Alexey Kuznetsov
 *
 * All rights reserved. 
 */

package org.localstorm.comet;

import com.google.gson.Gson;

public class JsonOutputBuilder extends OutputBuilder {

	public JsonOutputBuilder() {
	}
	
	@Override
	public String build(Event e) {
		Gson gson = new Gson();
		return gson.toJson(e.getData());
	}

	@Override
	public String endChunk() {
		return "]";
	}

	@Override
	public String getSeparator() {
		return ",";
	}

	@Override
	public String startChunk() {
		
		return "[";
	}

}
