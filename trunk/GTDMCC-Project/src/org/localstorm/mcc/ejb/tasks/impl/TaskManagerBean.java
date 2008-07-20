package org.localstorm.mcc.ejb.tasks.impl;

import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.localstorm.mcc.ejb.Constants;
import org.localstorm.mcc.ejb.contexts.Context;
import org.localstorm.mcc.ejb.except.DuplicateException;
import org.localstorm.mcc.ejb.except.ObjectNotFoundException;
import org.localstorm.mcc.ejb.lists.GTDList;
import org.localstorm.mcc.ejb.tasks.Task;
import org.localstorm.mcc.ejb.tasks.TaskManagerLocal;
import org.localstorm.mcc.ejb.tasks.TaskManagerRemote;
import org.localstorm.mcc.ejb.users.User;

/**
 *
 * @author Alexey Kuznetsov
 */
public class TaskManagerBean implements TaskManagerLocal, TaskManagerRemote 
{

    @Override
    public void createTask(Task task) throws DuplicateException 
    {
        try 
        {
            em.persist( task );
        } catch(EntityExistsException e) 
        {
            throw new DuplicateException(e);
        }
    }

    @Override
    public void updateTask(Task task) {
         em.merge( task );
    }
    

    @Override
    public Collection<Task> findByContext(Context ctx) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Task findById(int id) throws ObjectNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<Task> findByList(GTDList l) {
        throw new UnsupportedOperationException("Not supported yet.");
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

    @PersistenceContext(unitName=Constants.DEFAULT_PU)
    private EntityManager em;
}
