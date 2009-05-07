package org.localstorm.stocktracker.util.misc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

/**
 *
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


    public static Properties loadFromResource(String resourceName) throws IOException
    {
        InputStream is = null;

        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);

            if ( is==null ) {
                throw new IOException("Invalid resource name: ["+resourceName+"]");
            }

            Properties p = new Properties();
            p.load(is);

            return p;
        } finally {
            if (is!=null) {
                is.close();
            }
        }
    }
    
    
}
