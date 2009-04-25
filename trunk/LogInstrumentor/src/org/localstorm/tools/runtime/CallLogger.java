package org.localstorm.tools.runtime;


/**
 * @author Alexey Kuznetsov
 */
public class CallLogger
{
    private static final CallLogger ctl = new CallLogger();

    public static CallLogger getInstance()
    {
        return ctl;
    }
            
    public synchronized void logTime(String className, String methodName, long actualTime)
    {
        //throw new RuntimeException("Ops");
        System.out.println("Timing: "+className+"."+methodName+" ("+actualTime+" ms.)");
    }
}
