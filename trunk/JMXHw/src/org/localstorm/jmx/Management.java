package org.localstorm.jmx;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author localstorm
 */
public class Management implements ManagementMBean
{
    private AtomicBoolean needStop;
    private AtomicInteger loopCount;

    public Management(AtomicBoolean flag) {
        this.needStop = flag;
        this.loopCount = new AtomicInteger(0);
    }


    public int getLoopCount() {
        return this.loopCount.get();
    }

    public void incrementLoopCount() {
        this.loopCount.incrementAndGet();
    }

    public void stop() {
        this.needStop.set(true);
    }

}
