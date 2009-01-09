package org.localstorm.mcc.ejb.memcached;

import java.io.IOException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import net.spy.memcached.AddrUtil;
import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.MemcachedClientIF;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author localstorm
 */
public class MemcachedFasade {

    public  static final int EXPIRATION_IN_SECONDS = 3600;
    public  static final int TIMEOUT_IN_SECONDS    = 2;

    private static final Logger log = Logger.getLogger(MemcachedFasade.class);
    private static final MemcachedFasade instance = new MemcachedFasade();
    
    private MemcachedClientIF client;

    public static MemcachedFasade getInstance() {
        return instance;
    }

    // Shutdown?
    public MemcachedFasade() {
        try {
            String addresses = System.getProperty("memcached.server.instances");

            log.info("MEMCACHED SERVERS: "+((!StringUtils.isEmpty(addresses)) ? addresses : "NONE"));

            if (StringUtils.isEmpty(addresses)) {
                this.client = null;
            } else {
                this.client = new MemcachedClient(AddrUtil.getAddresses(addresses));

                Runtime.getRuntime().addShutdownHook(new Thread() {

                    @Override
                    public void run() {
                        log.info("MEMCACHED FASADE SHUTTING DOWN");
                        MemcachedFasade.this.client.shutdown();
                    }

                });
            }

        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Object get(String key) {

        if (this.client==null) {
            return null;
        }

        log.info("TRYING TO GET ["+key+"]");

        Future<Object> f = client.asyncGet(key);

        try {
            Object o = f.get(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS);
            if (o!=null) {
                log.info("OBJECT FOUND FOR ["+key+"]");
            }
            return o;
        } catch(Exception e) {
            log.error(e);
            f.cancel(false);
            log.info("NONE FOUND FOR ["+key+"]");
            return null;
        }
    }

    public void put(String key, Object o) {

        if (this.client==null) {
            return;
        }

        log.info("TRYING TO PUT ["+key+"]");
        client.set(key, EXPIRATION_IN_SECONDS, o);
    }

    public void remove(String key) {
        if (this.client==null) {
            return;
        }

        log.info("TRYING TO REMOVE ["+key+"]");
        client.delete(key);
    }

}
