package org.localstorm.threads;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Alexey Kuznetsov
 */
public class ThreadRegistrator
{
    private Map<String, Thread> threads;
    private final Object flag;

    public ThreadRegistrator()
    {
        this.threads = new HashMap<String, Thread>();
        this.flag = new Object();
    }

    public void register(String id, Thread thread)
    {
        synchronized(flag)
        {
            if (!threads.containsKey(id))
            {
                threads.put(id, thread);
            } else
            {
                throw new RuntimeException("Thread with this name is already registered!");
            }
        }
    }

    public void unregister(String id)
    {
        synchronized(flag)
        {
            this.threads.remove(id);
        }
    }

    public void kill(String id)
    {
        synchronized(flag)
        {
            Thread killable = this.threads.remove(id);
            if (killable!=null)
            {
                System.out.println("Halting thread identified by: ["+id+"]");
                killable.stop();
            }
        }
    }

    public void killAll()
    {
        synchronized(flag)
        {
            int i=0;
            for (Thread t: threads.values())
            {
                t.stop();
                i++;
            }

            if (i>0)
            {
                System.out.println("["+i+"] threads were forcibly halted!");
            }
            
            this.threads.clear();
        }
    }

    


}
