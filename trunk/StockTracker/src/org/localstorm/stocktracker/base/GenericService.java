package org.localstorm.stocktracker.base;

/**
 *
 * @author Alexey Kuznetsov
 */
public interface GenericService
{
    public void start() throws Exception;

    public void stop() throws Exception;

    public Runnable getShutdownRunnable();
}
