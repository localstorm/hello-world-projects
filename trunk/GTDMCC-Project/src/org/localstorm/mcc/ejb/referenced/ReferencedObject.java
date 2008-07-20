package org.localstorm.mcc.ejb.referenced;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * @author localstorm
 */
@Entity
@Table(name="REFERENCED_OBJECTS")
public class ReferencedObject implements Serializable 
{   
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="id")
    private Integer id;
    
    @Column(name="name", unique=false, updatable=true, nullable=false )
    private String name;
    
    @Column(name="is_archived", unique=false, updatable=true, nullable=false )    
    private boolean archived;
    
    @Column(name="creation", unique=false, updatable=true, nullable=false )    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creation;
    
    public ReferencedObject() 
    {
    
    }

    public ReferencedObject( String name ) {
        this.name = name;
        this.archived = false;
        this.creation = new Date();
    }

    
    public Integer getId() {
        return id;
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
}