package org.localstorm.stocktracker.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Alexey Kuznetsov
 */
public class Sequence 
{
    private AtomicLong value;

    public Sequence() {
        value = new AtomicLong(0);
    }

    public Sequence(long startValue) {
        value = new AtomicLong(startValue);
    }

    public long next() {
        return this.value.getAndAdd(1);
    }
}
