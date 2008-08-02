package org.localstorm.mcc.ejb.tasks.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.localstorm.mcc.ejb.AbstractManager;
import org.localstorm.mcc.ejb.Constants;
import org.localstorm.mcc.ejb.contexts.Context;
import org.localstorm.mcc.ejb.lists.GTDList;
import org.localstorm.mcc.ejb.tasks.Task;
import org.localstorm.mcc.ejb.tasks.TaskManagerLocal;
import org.localstorm.mcc.ejb.tasks.TaskManagerRemote;
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
    public Collection<Task> findByContext(Context ctx) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<Task> findByList(GTDList l) {
        Query tq = em.createNamedQuery(Task.Queries.FIND_BY_LIST);
        tq.setParameter(Task.Properties.LIST, l);
        
        List<Task> list = tq.getResultList();
        System.out.println("RETURNED: "+list.size());
        return list;
    }

    @Override
    public Collection<Task> findByUserAndLines(User user, Date redLine, Date deadLine) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<Task> findDelegatedByUserAndLines(User user, Date redLine, Date deadLine) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<Task> findIncompleteByUserAndLines(User user, Date redLine, Date deadLine) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<Task> findPausedByUserAndLines(User user, Date redLine, Date deadLine) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<Task> findStartedByUserAndLines(User user, Date redLine, Date deadLine) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
