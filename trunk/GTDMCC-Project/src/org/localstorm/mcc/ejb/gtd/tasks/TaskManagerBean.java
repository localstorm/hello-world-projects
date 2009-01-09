package org.localstorm.mcc.ejb.gtd.tasks;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import org.localstorm.mcc.ejb.AbstractManager;
import org.localstorm.mcc.ejb.gtd.contexts.Context;
import org.localstorm.mcc.ejb.gtd.lists.GTDList;
import org.localstorm.mcc.ejb.users.User;

/**
 *
 * @author Alexey Kuznetsov
 */
@Stateless
public class TaskManagerBean extends AbstractManager<Task>
                             implements TaskManagerLocal, TaskManagerRemote 
{
    public TaskManagerBean() {
        super(Task.class);
    }

    @Override
    public Collection<Task> findByLoE(User user, Context ctx, Effort effort) {
        Query tq;
        if (ctx==null) {
            tq = em.createNamedQuery(Task.Queries.FIND_LOE);
            tq.setParameter(Task.Properties.USER, user);
        } else {
            tq = em.createNamedQuery(Task.Queries.FIND_LOE_BY_CTX);
            tq.setParameter(Task.Properties.CTX, ctx);
        }
        tq.setParameter(Task.Properties.EFFORT, effort.getEffort());

        List<Task> list = tq.getResultList();
        super.updateObjectsMap(list);
        return list;
    }

    @Override
    public Collection<Task> findOldestOperative(Context ctx, int max) {
        Query tq = em.createNamedQuery(Task.Queries.FIND_OLDEST_BY_CTX);
        tq.setParameter(Task.Properties.CTX, ctx);
        tq.setMaxResults(max);

        List<Task> list = tq.getResultList();
        super.updateObjectsMap(list);
        return list;
    }

    @Override
    public Collection<Task> findOpeartiveByList(GTDList l) {
        Query tq = em.createNamedQuery(Task.Queries.FIND_BY_LIST);
        tq.setParameter(Task.Properties.LIST, l);
        
        List<Task> list = tq.getResultList();
        super.updateObjectsMap(list);
        return list;
    }
    
    @Override
    public Collection<Task> findAwaitedByList(GTDList l)
    {
        Query tq = em.createNamedQuery(Task.Queries.FIND_BY_LIST_AWAITED);
        tq.setParameter(Task.Properties.LIST, l);
        
        List<Task> list = tq.getResultList();
        super.updateObjectsMap(list);
        return list;
    }

    @Override
    public Collection<Task> findArchiveByList(GTDList l) {
        Query tq = em.createNamedQuery(Task.Queries.FIND_BY_LIST_ARCHIVED);
        tq.setParameter(Task.Properties.LIST, l);
        
        List<Task> list = tq.getResultList();
        super.updateObjectsMap(list);
        return list;
    }

    @Override
    public Collection<Task> findAwaited(User u, Context ctx) {
        Query tq;
        if (ctx==null) {
            tq = em.createNamedQuery(Task.Queries.FIND_AWAITED);
            tq.setParameter(Task.Properties.USER, u);
        } else {
            tq = em.createNamedQuery(Task.Queries.FIND_AWAITED_BY_CTX);
            tq.setParameter(Task.Properties.CTX, ctx);
        }
        
        List<Task> list = tq.getResultList();
        super.updateObjectsMap(list);
        return list;
    }

    @Override
    public Collection<Task> findRedlinedTasks(User user, Context ctx) {
        
        Query tq;
        if (ctx==null) {
            tq = em.createNamedQuery(Task.Queries.FIND_REDLINED);
            tq.setParameter(Task.Properties.USER, user);
            tq.setParameter(Task.Properties.NOW, new Date());
        } else {
            tq = em.createNamedQuery(Task.Queries.FIND_REDLINED_BY_CTX);
            tq.setParameter(Task.Properties.CTX, ctx);
            tq.setParameter(Task.Properties.NOW, new Date());
        }
        
        List<Task> list = tq.getResultList();
        super.updateObjectsMap(list);
        return list;
    }

    @Override
    public Collection<Task> findPending(User user, Context ctx) {
        Query tq;
        if (ctx==null) {
            tq = em.createNamedQuery(Task.Queries.FIND_UNFINISHED);
            tq.setParameter(Task.Properties.USER, user);
        } else {
            tq = em.createNamedQuery(Task.Queries.FIND_UNFINISHED_BY_CTX);
            tq.setParameter(Task.Properties.CTX, ctx);
        }

        List<Task> list = tq.getResultList();
        super.updateObjectsMap(list);
        return list;
    }


    @Override
    public Collection<Task> findFinished(User user, Context ctx) {
        Query tq;
        if (ctx==null) {
            tq = em.createNamedQuery(Task.Queries.FIND_FINISHED);
            tq.setParameter(Task.Properties.USER, user);
        } else {
            tq = em.createNamedQuery(Task.Queries.FIND_FINISHED_BY_CTX);
            tq.setParameter(Task.Properties.CTX, ctx);
        }

        List<Task> list = tq.getResultList();
        super.updateObjectsMap(list);
        return list;
    }

    @Override
    public Collection<Task> findDeadlinedTasks(User user, Context ctx) {
        Query tq;
        if (ctx==null) {
            tq = em.createNamedQuery(Task.Queries.FIND_DEADLINED);
            tq.setParameter(Task.Properties.USER, user);
            tq.setParameter(Task.Properties.NOW, new Date());
        } else {
            tq = em.createNamedQuery(Task.Queries.FIND_DEADLINED_BY_CTX);
            tq.setParameter(Task.Properties.CTX, ctx);
            tq.setParameter(Task.Properties.NOW, new Date());
        }
        
        List<Task> list = tq.getResultList();
        super.updateObjectsMap(list);
        return list;
    }

    @Override
    public Collection<Task> findAllByUser(User user) {
        Query tq = em.createNamedQuery(Task.Queries.FIND_BY_USER);

        tq.setParameter(Task.Properties.USER, user);
        List<Task> list = tq.getResultList();
        super.updateObjectsMap(list);
        return list;
    }

    @Override
    public void removeFinishedTasks(User user) {
        Query tq = em.createNamedQuery(Task.Queries.FIND_CLEANABLE_BY_USER);
        tq.setParameter(Task.Properties.USER, user);
        List<Task> list = tq.getResultList();

        for (Task t: list)
        {
            super.remove(t);
        }
    }

    @Override
    public Collection<Task> findScheduledNonFinishedTasks(User user) {
        Query tq = em.createNamedQuery(Task.Queries.FIND_SCHEDULED_BY_USER);
        tq.setParameter(Task.Properties.USER, user);

        List<Task> list = tq.getResultList();
        super.updateObjectsMap(list);
        return list;
    }

}
