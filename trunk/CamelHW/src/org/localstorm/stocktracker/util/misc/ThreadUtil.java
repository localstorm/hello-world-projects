package org.localstorm.stocktracker.util.misc;

/**
 *
 * @author Alexey Kuznetsov
 */
public class ThreadUtil
{
    private static final long DEFAULT_SLEEP_DELAY = 5000;

    public static void sleep(int delay)
    {
        try {
            System.out.println("Sleeping: "+Thread.currentThread().getName());
            Thread.sleep(delay);
        } catch(InterruptedException e) {
            // ignore
        }
    }

    public static void waitForInterruption()
    {
        try {
            while(true) {
                Thread.sleep(DEFAULT_SLEEP_DELAY);
            }
        } catch(InterruptedException e) {
            // ignore
        }
    }

}
