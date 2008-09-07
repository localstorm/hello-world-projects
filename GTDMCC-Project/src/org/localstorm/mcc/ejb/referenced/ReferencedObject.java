package org.localstorm.mcc.ejb.referenced;

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
import org.localstorm.mcc.ejb.Identifiable;
import org.localstorm.mcc.ejb.contexts.Context;


/**
 * @author localstorm
 */
@Entity
@Table(name="REFERENCED_OBJECTS")
public class ReferencedObject implements Serializable, Identifiable 
{   
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    @Column(name="name", unique=false, updatable=true, nullable=false )
    private String name;
    
    @Column(name="is_archived", unique=false, updatable=true, nullable=false )    
    private boolean archived;
    
    @Column(name="creation", unique=false, updatable=true, nullable=false )    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creation;
    
    @JoinColumn(name="context_id", nullable=false)
    @ManyToOne(fetch=FetchType.EAGER)
    private Context context;
    
    @Column(name="sort_order", unique=false, updatable=true, nullable=false )    
    private Integer sortOrder;

    
    public ReferencedObject() 
    {
    
    }

    public ReferencedObject( String name, Context ctx ) {
        this.name      = name;
        this.archived  = false;
        this.creation  = new Date();
        this.context   = ctx;
        this.sortOrder = Integer.valueOf(1);
    }
    
    public Integer getId() {
        return id;
    }

    public Context getContext() {
        return context;
    }
    
    public Date getCreation() {
        return creation;
    }

    public String getName() {
        return name;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }
    
    public void setCreation(Date creation) {
        this.creation = creation;
    }

    public void setContext(Context context) {
        this.context = context;
    }
    
}