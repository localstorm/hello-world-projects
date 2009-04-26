package org.localstorm.tools.aop.runtime;

/**
 *
 * @author Alexey Kuznetsov
 */
public interface CallProcessor
{
    public void logTime(String className, String methodName, long actualTime);
}
