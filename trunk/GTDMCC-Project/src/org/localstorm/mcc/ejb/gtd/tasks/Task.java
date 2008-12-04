package org.localstorm.mcc.ejb.gtd.tasks;

import org.localstorm.mcc.ejb.gtd.lists.GTDList;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.localstorm.mcc.ejb.Identifiable;


/**
 * @author localstorm
 */
@Entity
@Table(name="TASKS")
@NamedQueries({
    @NamedQuery(
        name = Task.Queries.FIND_BY_MAX_EFFORT,
        query= "SELECT o FROM Task o WHERE o.list.context.owner=:user and o.finished=false and o.cancelled=false and" +
        " o.effort<=:effort and o.delegated=false ORDER BY o.list.context.name, o.list.name"
    ),
    @NamedQuery(
        name = Task.Queries.FIND_DEADLINED,
        query= "SELECT o FROM Task o WHERE o.list.context.owner=:user and o.finished=false and o.cancelled=false and o.deadline<=:now" +
        " ORDER BY o.list.context.name, o.list.name"
    ),
    @NamedQuery(
        name = Task.Queries.FIND_REDLINED,
        query= "SELECT o FROM Task o WHERE o.list.context.owner=:user and o.finished=false and o.cancelled=false and o.redline<=:now and (o.deadline>:now or o.deadline is NULL)" +
        "  ORDER BY o.list.context.name, o.list.name"
    ),
    @NamedQuery(
        name = Task.Queries.FIND_BY_LIST,
        query= "SELECT o FROM Task o WHERE o.list=:list and o.finished=false and o.delegated=false and o.cancelled=false"
    ),
    @NamedQuery(
        name = Task.Queries.FIND_BY_LIST_ARCHIVED,
        query= "SELECT o FROM Task o WHERE o.list=:list and (o.finished=true or o.cancelled=true)"
    ),
    @NamedQuery(
        name = Task.Queries.FIND_BY_LIST_AWAITED,
        query= "SELECT o FROM Task o WHERE o.list=:list and o.finished=false and o.delegated=true"
    ),
    @NamedQuery(
        name = Task.Queries.FIND_ALL_AWAITED,
        query= "SELECT o FROM Task o WHERE o.finished=false and o.delegated=true and o.list.context.owner=:user" +
        " ORDER BY o.list.context.name, o.list.name"
    )
})
public class Task implements Identifiable, Serializable 
{   
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name="summary", unique=false, updatable=true, nullable=false )
    private String summary;
    
    @Column(name="details", unique=false, updatable=true, nullable=true )
    private String details;
    
    @Column(name="runtime_note", unique=false, updatable=true, nullable=true )
    private String runtimeNote;
    
    @Column(name="sort_order", unique=false, updatable=true, nullable=false )    
    private Integer sortOrder;
    
    @Column(name="effort", unique=false, updatable=true, nullable=false )    
    private int effort;
    
    @JoinColumn(name="list_id", nullable=false)
    @ManyToOne(fetch=FetchType.EAGER)
    private GTDList list;
    
    @Column(name="is_cancelled", unique=false, updatable=true, nullable=false )    
    private boolean cancelled;
    
    @Column(name="is_finished", unique=false, updatable=true, nullable=false )       
    private boolean finished;
    
    @Column(name="is_awaited", unique=false, updatable=true, nullable=false )    
    private boolean awaited;
    
    @Column(name="is_paused", unique=false, updatable=true, nullable=false )    
    private boolean paused;
    
    @Column(name="is_delegated", unique=false, updatable=true, nullable=false )    
    private boolean delegated;
    
    @Column(name="creation", unique=false, updatable=true, nullable=false )    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creation;
    
    @Column(name="deadline", unique=false, updatable=true, nullable=true )    
    @Temporal(TemporalType.TIMESTAMP)
    private Date deadline;
    
    @Column(name="redline", unique=false, updatable=true, nullable=true )    
    @Temporal(TemporalType.TIMESTAMP)
    private Date redline;

    public Task() 
    {
    
    }

    public Task(String summary, String details, GTDList list, Date deadline, Date redline) {
        this.summary = summary;
        this.details = details;
        this.list = list;
        this.deadline = deadline;
        this.redline = redline;
        this.finished = false;
        this.awaited = false;
        this.paused = true;
        this.delegated = false;
        this.creation = new Date();
        this.effort = 3;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public Date getCreation() {
        return creation;
    }

    public Date getDeadline() {
        return deadline;
    }

    public Date getRedline() {
        return redline;
    }

    public GTDList getList() {
        return list;
    }

    public String getDetails() {
        return details;
    }

    public String getSummary() {
        return summary;
    }

    public String getRuntimeNote() {
        return runtimeNote;
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean isDelegated() {
        return delegated;
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean isAwaited() {
        return awaited;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public int getEffort() {
        return effort;
    }
    
    public void setCancelled(boolean cancelled) {
        this.cancelled  = cancelled;
    }

    public void setFinished(boolean accomplished) {
        this.finished = accomplished;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public void setDelegated(boolean delegated) {
        this.delegated = delegated;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setList(GTDList list) {
        this.list = list;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void setRedline(Date redline) {
        this.redline = redline;
    }

    public void setRuntimeNote(String runtimeNote) {
        this.runtimeNote = runtimeNote;
    }

    public void setAwaited(boolean started) {
        this.awaited = started;
    }
    
    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setEffort(int effort) {
        this.effort = effort;
    }
    
    public static interface Queries {
        public static final String FIND_BY_MAX_EFFORT        = "findByMaxEffort";
        public static final String FIND_ALL_AWAITED      = "findAllAwaitedTasks";
        public static final String FIND_BY_LIST          = "findByList";
        public static final String FIND_BY_LIST_ARCHIVED = "findByListArchived";
        public static final String FIND_BY_LIST_AWAITED  = "findByListAwaited";
        public static final String FIND_REDLINED         = "findRedlined";
        public static final String FIND_DEADLINED        = "findDeadlined";
    }
    
    public static interface Properties
    {
        public static final String CTX  = "ctx";
        public static final String LIST = "list";
        public static final String USER = "user";
        public static final String NOW  = "now";
        public static final String EFFORT = "effort";
    }

}
