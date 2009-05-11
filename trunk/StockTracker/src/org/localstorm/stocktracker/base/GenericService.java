package org.localstorm.stocktracker.base;

/**
 * Base interface of background service tasks
 * @author Alexey Kuznetsov
 */
public interface GenericService
{
    /**
    * Starts service
    */
    public void start() throws Exception;

    /**
    * Stops service
    */
    public void stop() throws Exception;

    /**
    * Returns Runnable instance that shuts service down
    */
    public Runnable getShutdownRunnable();
}
