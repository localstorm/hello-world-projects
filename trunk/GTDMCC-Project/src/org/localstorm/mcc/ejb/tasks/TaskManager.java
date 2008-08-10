package org.localstorm.mcc.ejb.tasks;

import java.util.Collection;
import org.localstorm.mcc.ejb.BaseManager;
import org.localstorm.mcc.ejb.contexts.Context;
import org.localstorm.mcc.ejb.lists.GTDList;

/**
 *
 * @author Alexey Kuznetsov
 */
public interface TaskManager extends BaseManager<Task>
{
    public static final String BEAN_NAME = "TaskManagerBean";
    
    //TODO!
    /* Doesn't return archived contexts */
    public Collection<Task> findOpeartiveByList(GTDList l);
    
    public Collection<Task> findAwaitedByList(GTDList l);
    
    public Collection<Task> findArchiveByList(GTDList l);
    
    /* Doesn't return archived contexts */
    public Collection<Task> findByContext(Context ctx);
    
    
}
