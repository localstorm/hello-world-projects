package org.localstorm.mcc.ejb.gtd.tasks;

import java.util.Collection;
import org.localstorm.mcc.ejb.BaseManager;
import org.localstorm.mcc.ejb.gtd.contexts.Context;
import org.localstorm.mcc.ejb.gtd.lists.GTDList;
import org.localstorm.mcc.ejb.users.User;

/**
 *
 * @author Alexey Kuznetsov
 */
public interface TaskManager extends BaseManager<Task>
{
    public static final String BEAN_NAME = "TaskManagerBean";

    public Collection<Task> findAllByUser(User user);

    public boolean isCleanupNeeded(User user);

    public void cleanup(User user);
    
    public Collection<Task> findOpeartiveByList(GTDList l);
    
    public Collection<Task> findAwaitedByList(GTDList l);
    
    public Collection<Task> findArchiveByList(GTDList l);
    
    /* Doesn't return archived contexts */
    public Collection<Task> findAllAwaited(User u);

    public Collection<Task> findRedlinedTasks(User user);

    public Collection<Task> findDeadlinedTasks(User user);

    public Collection<Task> findOldestOperative(Context ctx, int MAX_OLDEST_TASKS);

    public Collection<Task> findScheduledNonFinishedTasks(User user);

    /**
     * @param ctx May be null!
     */
    public Collection<Task> findByMaxEffort(Effort effort, User user);
    
    
}
