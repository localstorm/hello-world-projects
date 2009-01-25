package org.localstorm.mcc.ejb.gtd.tasks.dao;


import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import org.localstorm.mcc.ejb.AbstractEntity;

/**
 *
 * @author localstorm
 */
public class FiredHintsReportBean extends AbstractEntity
{
    private Collection<TaskStub> fired;

    public FiredHintsReportBean() {
        this.fired = new LinkedList<TaskStub>();
    }

    public Collection<TaskStub> getFired() {
        return Collections.unmodifiableCollection(fired);
    }

    public void addFired(String taskName, Integer taskId, Integer listId)
    {
        this.fired.add(new TaskStub(taskName, taskId, listId));
    }


    public static final class TaskStub extends AbstractEntity
    {
        private String summary;
        private int id;
        private int listId;

        public TaskStub(String summary, int id, int listId) {
            this.summary   = summary;
            this.id     = id;
            this.listId = listId;
        }

        public int getId() {
            return id;
        }

        public String getSummary() {
            return summary;
        }

        public int getListId() {
            return listId;
        }
    }
}
