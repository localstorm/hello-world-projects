package org.localstorm.mcc.ejb.lists;

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
import org.localstorm.mcc.ejb.contexts.Context;


/**
 * @author localstorm
 */
@Entity
@Table(name="LISTS")
public class GTDList implements Serializable {   
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="id")
    private Integer id;
    
    @Column(name="name", unique=false, updatable=true, nullable=false )
    private String name;
    
    @Column(name="sort_order", unique=false, updatable=true, nullable=false )    
    private Integer sortOrder;
    
    @JoinColumn(name="context_id", nullable=false)
    @ManyToOne(fetch=FetchType.EAGER)
    private Context context;
    
    @Column(name="is_archived", unique=false, updatable=true, nullable=false )    
    private boolean archived;
    
    @Column(name="creation", unique=false, updatable=true, nullable=false )    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creation;

    public GTDList() 
    {
    
    }

    public GTDList( String name, Context ctx ) {
        this.name       = name;
        this.context    = ctx;
        this.archived   = false;
        this.creation   = new Date();
    }
    
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public Context getContext() {
        return context;
    }

    public Date getCreation() {
        return creation;
    }

    public boolean isArchived() {
        return archived;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public void setContext(Context context) {
        this.context = context;
    }

}
