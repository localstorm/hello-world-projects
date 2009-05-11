package org.localstorm.stocktracker.util.misc;

import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

/**
 * Utility class to handle Properties
 * @author Alexey Kuznetsov
 */
public class PropertiesUtil
{

    public static Map<String, String> asMap(Properties prop)
    {
        Map<String, String> map = new TreeMap<String, String>();
        
        for(Map.Entry<Object, Object> e: prop.entrySet()) {
            map.put((String) e.getKey(), (String) e.getValue());
        }

        return map;
    }

}
