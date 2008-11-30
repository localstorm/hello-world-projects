package org.localstorm.mcc.ejb.gtd.tasks;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import org.localstorm.mcc.ejb.AbstractManager;
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
    public Collection<Task> findOpeartiveByList(GTDList l) {
        Query tq = em.createNamedQuery(Task.Queries.FIND_BY_LIST);
        tq.setParameter(Task.Properties.LIST, l);
        
        List<Task> list = tq.getResultList();
        return list;
    }
    
    @Override
    public Collection<Task> findAwaitedByList(GTDList l)
    {
        Query tq = em.createNamedQuery(Task.Queries.FIND_BY_LIST_AWAITED);
        tq.setParameter(Task.Properties.LIST, l);
        
        List<Task> list = tq.getResultList();
        return list;
    }

    @Override
    public Collection<Task> findArchiveByList(GTDList l) {
        Query tq = em.createNamedQuery(Task.Queries.FIND_BY_LIST_ARCHIVED);
        tq.setParameter(Task.Properties.LIST, l);
        
        List<Task> list = tq.getResultList();
        System.out.println("RETURNED: "+list.size());
        return list;
    }

    @Override
    public Collection<Task> findAllAwaited(User u) {
        Query tq = em.createNamedQuery(Task.Queries.FIND_ALL_AWAITED);
        tq.setParameter(Task.Properties.USER, u);
        
        List<Task> list = tq.getResultList();
        System.out.println("RETURNED: "+list.size());
        return list;
    }

    @Override
    public Collection<Task> findRedlinedTasks(User user) {
        Query tq = em.createNamedQuery(Task.Queries.FIND_REDLINED);
        tq.setParameter(Task.Properties.USER, user);
        tq.setParameter(Task.Properties.NOW, new Date());
        
        List<Task> list = tq.getResultList();
        return list;
    }

    @Override
    public Collection<Task> findDeadlinedTasks(User user) {
        Query tq = em.createNamedQuery(Task.Queries.FIND_DEADLINED);
        tq.setParameter(Task.Properties.USER, user);
        tq.setParameter(Task.Properties.NOW, new Date());
        
        List<Task> list = tq.getResultList();
        return list;
    }

    @Override
    public Collection<Task> findByMaxEffort(Effort effort, User user) {
        Query tq = em.createNamedQuery(Task.Queries.FIND_BY_MAX_EFFORT);
                
        tq.setParameter(Task.Properties.USER, user);
        tq.setParameter(Task.Properties.EFFORT, effort.getEffort());
        List<Task> list = tq.getResultList();
        return list;
    }
    
    
    
    
}
