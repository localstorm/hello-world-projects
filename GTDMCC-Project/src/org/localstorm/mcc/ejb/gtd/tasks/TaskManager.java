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

    public Collection<Task> findAwaited(User user, Context ctx);

    public Collection<Task> findDeadlinedTasks(User user, Context ctx);

    public Collection<Task> findFinished(User user, Context context);

    public Collection<Task> findRedlinedTasks(User user, Context ctx);

    public Collection<Task> findPending(User user, Context ctx);

    public boolean isCleanupNeeded(User user);

    public void cleanup(User user);
    
    public Collection<Task> findOpeartiveByList(GTDList l);
    
    public Collection<Task> findAwaitedByList(GTDList l);
    
    public Collection<Task> findArchiveByList(GTDList l);
    
    /* Doesn't return archived contexts */

    public Collection<Task> findOldestOperative(Context ctx, int maxOldestTasks);

    public Collection<Task> findScheduledNonFinishedTasks(User user);

    /**
     * @param ctx May be null!
     */
    public Collection<Task> findByMaxEffort(Effort effort, User user);
    
    
}
