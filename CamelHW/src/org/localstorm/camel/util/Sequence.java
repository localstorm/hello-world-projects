package org.localstorm.camel.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author Alexey Kuznetsov
 */
public class Sequence 
{
    private AtomicLong value;

    public Sequence() {
        value = new AtomicLong(0);
    }

    public long next() {
        return this.value.getAndAdd(1);
    }
}
