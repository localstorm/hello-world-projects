package org.localstorm.tools.aop.runtime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author Alexey Kuznetsov
 */
public class HashMapUniversalCallProcessor implements DeadlineCallProcessor, CallProcessor
{
    private final HashMap<String, CallInfo> deadlined;
    private final HashMap<String, CallInfo> stat;

    public HashMapUniversalCallProcessor()
    {
        this.deadlined = new HashMap<String, CallInfo>(100);
        this.stat      = new HashMap<String, CallInfo>(1000);
    }

    @Override
    public void logTime(String className, String methodName, long deadline, long actualTime)
    {
        synchronized(this.deadlined)
        {
            String key = getKey(className, methodName);

            CallInfo ci = this.deadlined.get(key);
            if (ci!=null) {
                ci.newCall(actualTime);
            } else {
                ci = new CallInfo(className, methodName, deadline, actualTime);
                this.deadlined.put(key, ci);
            }
        }
    }

    @Override
    public void logTime(String className, String methodName, long actualTime)
    {
        synchronized(this.stat)
        {
            String key = getKey(className, methodName);

            CallInfo ci = this.stat.get(key);
            if (ci!=null) {
                ci.newCall(actualTime);
            } else {
                ci = new CallInfo(className, methodName, -1, actualTime);
                this.stat.put(key, ci);
            }
        }
    }

    public Collection<CallInfo> getStatistics()
    {
        synchronized(this.stat)
        {
            Collection<CallInfo> result = new ArrayList<CallInfo>(this.stat.size());
            for (CallInfo ci: this.stat.values()) {
                result.add(ci.clone());
            }
            return result;
        }
    }


    public static final class CallInfo implements Serializable, Cloneable
    {
        private String methodName;
        private String className;
        private long callCount;
        private long deadline;
        private long avgDuration;

        public CallInfo(String className, String methodName, long deadline, long firstDuration)
        {
            this.methodName  = methodName;
            this.className   = className;
            this.callCount   = 1;
            this.avgDuration = firstDuration;
            this.deadline    = deadline;
        }

        public long getAvgDuration()
        {
            return avgDuration;
        }

        public long getCallCount()
        {
            return callCount;
        }

        public String getClassName()
        {
            return className;
        }

        public String getMethodName()
        {
            return methodName;
        }

        public void newCall(long duration)
        {
            long raw = this.avgDuration*this.callCount+duration;
            this.callCount++;
            this.avgDuration=raw/this.callCount;
        }

        @Override
        protected CallInfo clone()
        {
            try
            {
                return (CallInfo) super.clone();
            } catch (CloneNotSupportedException ex) {
                throw new RuntimeException(ex);
            }
        }

        @Override
        public String toString()
        {
            StringBuilder sb = new StringBuilder(getKey(className, methodName));
            sb.append(": (");
            sb.append("deadline=");
            sb.append(this.deadline);
            sb.append(", avgDuration=");
            sb.append(this.avgDuration);
            sb.append(") callCount=");
            sb.append(callCount);
            return sb.toString();
        }

    }

    private static String getKey(String className, String methodName)
    {
        StringBuilder sb = new StringBuilder(className.length()+1+methodName.length());
        sb.append(className);
        sb.append('.');
        sb.append(methodName);
        return sb.toString();
    }

}
