package org.localstorm.tools.crash;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 *
 * @author Alexey Kuznetsov
 */
public abstract class ExceptionHandlerPrototype implements Cloneable, Thread.UncaughtExceptionHandler
{

    public UncaughtExceptionHandler getByPrototype()
    {
        try {
            ExceptionHandlerPrototype clone = (ExceptionHandlerPrototype) this.clone();
            clone.cleanup();
            return clone;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public abstract void uncaughtException(Thread t, Throwable e);

    public abstract void cleanup();

}
