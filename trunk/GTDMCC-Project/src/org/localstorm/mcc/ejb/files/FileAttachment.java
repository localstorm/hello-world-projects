package org.localstorm.mcc.ejb.files;

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
import org.localstorm.mcc.ejb.Identifiable;

/**
 *
 * @author Alexey Kuznetsov
 */
@Entity
@Table(name="FILES")
public class FileAttachment implements Identifiable, Serializable
{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    @Column(name="name", unique=false, updatable=true, nullable=false )
    private String name;
    
    @Column(name="description", unique=false, updatable=true, nullable=false )
    private String description;
    
    @Column(name="mime_type", unique=false, updatable=true, nullable=false )
    private String mimeType;

    @Column(name="creation", unique=false, updatable=true, nullable=false )    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creation;

    public FileAttachment() {
        this.creation = new Date();
    }
    
    @Override
    public Integer getId() {
        return this.id;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
