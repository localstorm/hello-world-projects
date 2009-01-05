/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.localstorm.mcc.web;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author localstorm
 */
public class ExpirableValuesCache<K, T> {

    private SoftReference<Object> sr;
    private ReferenceQueue<Object> rq;
    private Map<K, Logged<T>> store;
    private long expiration;
    private long lastCleanup;

    public ExpirableValuesCache(long expirationMillis)
    {
        this.rq = new ReferenceQueue<Object>();
        this.store = new HashMap<K, Logged<T>>();
        this.expiration = expirationMillis;
        this.reloadTrigger();
    }

    public T get(K key)
    {
        this.checkLog();
        Logged<T> log = store.get(key);

        if (log!=null) {
            log.update();
            return log.getObject();
        } else {
            return null;
        }

    }

    public void put(K key, T value)
    {
        this.checkLog();
        store.put(key, new Logged(value));
    }

    private void checkLog() {
        Reference<? extends Object> ref = rq.poll();
        if (ref!=null && lastCleanup<(System.currentTimeMillis()-expiration))
        {
            this.reloadTrigger();

            for (Iterator<Map.Entry<K, Logged<T>>> it = store.entrySet().iterator(); it.hasNext(); )
            {
                Logged<T> logged = it.next().getValue();
                if (logged.expired(expiration))
                {
                    it.remove();
                }
            }
        }
    }

    private void reloadTrigger() {
        Object trigger = new Object();
        this.sr = new SoftReference(trigger, rq);
    }

    private static class Logged<T>
    {
        private T obj;
        private long ts;

        public Logged(T obj)
        {
            this.obj = obj;
            this.ts = System.currentTimeMillis();
        }

        public T getObject() {
            return this.obj;
        }

        public boolean expired(long expiration) {
            return ts<(System.currentTimeMillis()-expiration);
        }

        private void update() {
            this.ts = System.currentTimeMillis();
        }
    }

}
