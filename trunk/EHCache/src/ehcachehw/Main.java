/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ehcachehw;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 *
 * @author localstorm
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        useAsSingleton();
        useAsObject();
    }

    private static void useAsObject()
    {
        CacheManager cm = new CacheManager();

        System.out.println("CACHE names:");

        String[] cacheNames = CacheManager.getInstance().getCacheNames();
        for (String name:cacheNames)
        {
            System.out.println("C: " + name);
        }

        
        Cache c = cm.getCache("translationTables");

        
        //c.put(new Element("fuck", "fuck-fuck"));

        //c.put(new Element("fuck", "fuck-fuck!"));

        System.out.println(c.get("fuck").getValue());

        c.remove("fuck");

        System.out.println(c.get("fuck") == null);

        cm.shutdown();
    }

    private static void useAsSingleton()
            throws CacheException,
                   IllegalStateException,
                   ClassCastException,
                   IllegalArgumentException
    {
        CacheManager cm = CacheManager.getInstance();

        System.out.println("CACHE names:");

        String[] cacheNames = CacheManager.getInstance().getCacheNames();
        for (String name:cacheNames)
        {
            System.out.println("C: " + name);
        }


        Cache c = cm.getCache("translationTables");


        c.put(new Element("fuck", "fuck-fuck"));

        c.put(new Element("fuck", "fuck-fuck!"));

        System.out.println(c.get("fuck").getValue());

        c.remove("fuck");

        System.out.println(c.get("fuck") == null);

        c.put(new Element("fuck", "fuck-fuck!"));
        
        //CacheManager.getInstance().shutdown();
    }

}


