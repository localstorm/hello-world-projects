package org.localstorm.tools.aop.runtime;


/**
 * @author Alexey Kuznetsov
 */
public class DeadlineCallLogger
{
    private static final DeadlineCallLogger ctl = new DeadlineCallLogger();

    public static DeadlineCallLogger getInstance()
    {
        return ctl;
    }
            
    public synchronized void logTime(String className, String methodName, long deadline, long actualTime)
    {
        //throw new RuntimeException("Ops");
        System.out.println("Deadline: "+className+"."+methodName+" ("+actualTime+" ms.) but "+deadline+" ms. expected!");
    }
}
