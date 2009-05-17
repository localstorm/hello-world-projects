package org.localstorm.tools.crash;

import java.util.LinkedList;

/**
 *
 * @author Alexey Kuznetsov
 */
public class ChainingExceptionHandler implements Thread.UncaughtExceptionHandler,
                                                 Cloneable
{
    public ChainingExceptionHandler() {
        this.handlers = new LinkedList<Thread.UncaughtExceptionHandler>();
    }

    public void invokeBefore(Thread.UncaughtExceptionHandler ueh) {
        this.handlers.add(0, ueh);
    }
    
    public void invokeAfter(Thread.UncaughtExceptionHandler ueh) {
        this.handlers.add(ueh);
    }

    public void uncaughtException(Thread t, Throwable e) {

        for (Thread.UncaughtExceptionHandler ueh: this.handlers) {
            try {
                ueh.uncaughtException(t, e);
            } catch(Throwable ex) {
                // must ignore
            }
        }
        
    }

    private final LinkedList<Thread.UncaughtExceptionHandler> handlers;
}

