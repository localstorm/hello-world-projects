package org.localstorm.tools.aop.runtime;

/**
 *
 * @author Alexey Kuznetsov
 */
public interface DeadlineCallProcessor
{
    public void logTime(String className, String methodName, long deadline, long actualTime);
}
