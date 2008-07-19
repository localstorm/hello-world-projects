package org.localstorm.mcc.ejb.tasks;

import org.localstorm.mcc.ejb.lists.*;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * @author localstorm
 */
@Entity
@Table(name="TASKS")
public class Task implements Serializable 
{   
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="id")
    private Integer id;
    
    @Column(name="summary", unique=false, updatable=true, nullable=false )
    private String summary;
    
    @Column(name="details", unique=false, updatable=true, nullable=false )
    private String details;
    
    @Column(name="runtime_note", unique=false, updatable=true, nullable=false )
    private String runtimeNote;
    
    @Column(name="sort_order", unique=false, updatable=true, nullable=false )    
    private Integer sortOrder;
    
    @JoinColumn(name="list_id", nullable=false)
    @ManyToOne(fetch=FetchType.EAGER)
    private GTDList list;
    
    @Column(name="is_accomplished", unique=false, updatable=true, nullable=false )    
    private boolean accomplished;
    
    @Column(name="is_started", unique=false, updatable=true, nullable=false )    
    private boolean started;
    
    @Column(name="is_paused", unique=false, updatable=true, nullable=false )    
    private boolean paused;
    
    @Column(name="is_delegated", unique=false, updatable=true, nullable=false )    
    private boolean delegated;
    
    @Column(name="creation", unique=false, updatable=true, nullable=false )    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creation;
    
    @Column(name="deadline", unique=false, updatable=true, nullable=false )    
    @Temporal(TemporalType.TIMESTAMP)
    private Date deadline;
    
    @Column(name="redline", unique=false, updatable=true, nullable=false )    
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
        this.accomplished = false;
        this.started = false;
        this.paused = true;
        this.delegated = false;
        this.creation = new Date();
    }

    public Integer getId() {
        return id;
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

    public boolean isAccomplished() {
        return accomplished;
    }

    public boolean isDelegated() {
        return delegated;
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean isStarted() {
        return started;
    }

    public void setAccomplished(boolean accomplished) {
        this.accomplished = accomplished;
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

    public void setStarted(boolean started) {
        this.started = started;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

}
