import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.localstorm.cache.invalidator.ScriptableCacheInvalidator;

/**
 * Copyright 1986-2009 Alexey Kuznetsov
 *
 * All rights reserved. 
 */
public class Main {
	
	public static void main(String...args) throws Exception
	{
		InputStream is = null;
		try {
			is = Main.class.getResourceAsStream("/finance_caches.dsl");
			String script = IOUtils.toString(is);

			ScriptableCacheInvalidator sci = new ScriptableCacheInvalidator(script){
				@Override
				public void invalidate(String name, Map<String, String> params) {
					System.out.println("Invalidating: "+name+" params: "+params);
				}
			};

			sci.handleResourceChange("ASSETS", newMap("id", 10, "user_id", 12));

		} finally {
			IOUtils.closeQuietly(is);
		}
	}

	private static Map<String, String> newMap(Object ... keyValueSeq) {
		if (keyValueSeq.length %2 !=0){
			throw new IllegalArgumentException("Number of arguments % 2 != 0");
		}
		Map<String, String> res = new LinkedHashMap<String, String>();
		
		for (int i=0; i<keyValueSeq.length; i+=2) {
			res.put(keyValueSeq[i].toString(), keyValueSeq[i+1].toString());
		}
		
		return res;
	}
}
