package org.localstorm.mcc.ejb.gtd.entity;

import org.localstorm.mcc.ejb.AbstractEntity;
import org.localstorm.mcc.ejb.Identifiable;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/**
 * @author localstorm
 */
@Entity
@Table(name="HINTS")
@NamedQueries({
    @NamedQuery(
        name = Hint.Queries.FIND_BY_TASK,
        query= "SELECT o FROM Hint o WHERE o.task=:task"
    ),
    @NamedQuery(
        name = Hint.Queries.UPDATE_BY_TASK,
        query= "UPDATE Hint h SET h.lastUpdate=:lastUpdate WHERE h.task=:task"
    ),
    @NamedQuery(
        name = Hint.Queries.DISCARD_BY_TASK,
        query= "DELETE Hint h WHERE h.task=:task"
    )
})
public class Hint extends AbstractEntity implements Identifiable
{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @JoinColumn(name="task_id", nullable=false)
    @ManyToOne(fetch=FetchType.LAZY)
    private Task   task;

    @Column(name="hint_condition", unique=false, updatable=true, nullable=true )
    private String hc;

    @Column(name="last_update", unique=false, updatable=true, nullable=false )
    @Temporal(TemporalType.TIMESTAMP)
    private Date   lastUpdate;
    private static final long serialVersionUID = -8078474234686322486L;

    public Hint() {
    }

    public Hint(Task t, HintCondition hc)
    {
        this.task = t;
        this.hc   = hc.toString();
        
        Calendar cal = Calendar.getInstance();
        {
            cal.add(Calendar.YEAR, -100);
        }
        
        this.lastUpdate    = cal.getTime();
    }

    public HintCondition getHintCondition() {
        return HintCondition.valueOf(hc);
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Task getTask() {
        return task;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    protected void setHc(String hc) {
        this.hc = hc;
    }

    public static interface Queries
    {
        public static String FIND_BY_TASK    = "findHintsByTask";
        public static String UPDATE_BY_TASK  = "updateHintsByTask";
        public static String DISCARD_BY_TASK = "discardHintsByTask";
    }

    public static interface Properties
    {
        public static final String TASK         = "task";
        public static final String LAST_UPDATE  = "lastUpdate";
    }
   
}
