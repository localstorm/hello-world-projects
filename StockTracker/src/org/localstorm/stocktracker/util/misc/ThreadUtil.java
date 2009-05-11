package org.localstorm.stocktracker.util.misc;

/**
 * Utility class to ignore Interrupted Exception in some way
 * @author Alexey Kuznetsov
 */
public class ThreadUtil
{
    private static final long DEFAULT_SLEEP_DELAY = 50000;

    public static boolean sleep(int delay)
    {
        try {
            Thread.sleep(delay);
            return true;
        } catch(InterruptedException e) {
            return false;
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
