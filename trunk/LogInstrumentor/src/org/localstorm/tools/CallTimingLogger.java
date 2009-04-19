package org.localstorm.tools;


/**
 * TODO: Thread safety!
 * @author Alexey Kuznetsov
 */
public class CallTimingLogger 
{
    private static final CallTimingLogger ctl = new CallTimingLogger();

    public static CallTimingLogger getInstance()
    {
        return ctl;
    }
            
    public void logTime(String className, String methodName, long actualTime)
    {
        System.out.println("Timing: "+className+"."+methodName+" ("+actualTime+")");
    }
}
