package org.localstorm.stocktracker;

/**
 *
 * @author Alexey Kuznetsov
 */
public interface ServiceFasade
{
    public void start() throws Exception;

    public void stop() throws Exception;

    public Runnable getShutdownRunnable();
}
