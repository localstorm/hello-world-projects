package org.localstorm.mcc.web;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import org.localstorm.mcc.ejb.lists.GTDList;
import org.localstorm.mcc.ejb.tasks.Task;

/**
 *
 * @author Alexey Kuznetsov
 */
public class Clipboard 
{
    private Map<Integer, GTDList> lists;
    private Map<Integer, Task> tasks;
    
    public Clipboard() {
        lists = new TreeMap<Integer, GTDList>();
        tasks = new TreeMap<Integer, Task>();
    }
    
    public Collection<GTDList> getLists()
    {
        return this.lists.values();
    }

    public Collection<Task> getTasks() {
        return this.tasks.values();
    }
    
    public GTDList pickList(Integer id)
    {
        return this.lists.remove(id);
    }
    
    public Task pickTask(Integer id)
    {
        return this.tasks.remove(id);
    }
    
    public void copyTask(Task t)
    {
        this.tasks.put(t.getId(), t);
    }
    
    public void copyList(GTDList l)
    {
        this.lists.put(l.getId(), l);
    }
}
