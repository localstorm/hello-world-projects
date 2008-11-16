package org.localstorm.mcc.ejb.tasks;

import java.util.Collection;
import org.localstorm.mcc.ejb.BaseManager;
import org.localstorm.mcc.ejb.contexts.Context;
import org.localstorm.mcc.ejb.lists.GTDList;
import org.localstorm.mcc.ejb.users.User;

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
    
    public Collection<Task> findAllAwaited(User u);

    public Collection<Task> findRedlinedTasks(User user);

    public Collection<Task> findDeadlinedTasks(User user);
    
    /**
     * @param ctx May be null!
     */
    public Collection<Task> findByMaxEffort(Context ctx, Effort effort, User user);
    
    
}
