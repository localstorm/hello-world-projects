/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package memcachedtest;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;


/**
 *
 * @author localstorm
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String s = "Hello, World!";
        
        String[] serverlist = { "localhost:11211" };
        SockIOPool pool = SockIOPool.getInstance();
        pool.setServers(serverlist);
        pool.initialize();

        MemCachedClient mc = new MemCachedClient();
        
        mc.add("$key$", s);
        
        s = (String)mc.get("$key$");
        
        System.out.println(s);

        
    }

}
