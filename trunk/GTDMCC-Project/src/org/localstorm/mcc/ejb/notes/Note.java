package org.localstorm.mcc.ejb.notes;

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
@Table(name="NOTES")
public class Note implements Serializable {   
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="id")
    private Integer id;
    
    @Column(name="note", unique=false, updatable=true, nullable=false )
    private String note;
    
    @Column(name="creation", unique=false, updatable=true, nullable=false )    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creation;

    public Note() 
    {
    
    }

    public Note( String note ) {
        this.note       = note;
        this.creation   = new Date();
    }
    
    public Integer getId() {
        return id;
    }

    public String getNote() {
        return note;
    }

    public Date getCreation() {
        return creation;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
