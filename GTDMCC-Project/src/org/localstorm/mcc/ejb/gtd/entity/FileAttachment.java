package org.localstorm.mcc.ejb.gtd.entity;

import org.localstorm.mcc.ejb.AbstractEntity;
import org.localstorm.mcc.ejb.Identifiable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Alexey Kuznetsov
 */
@Entity
@Table(name="FILES")
public class FileAttachment extends AbstractEntity implements Identifiable, Serializable
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
    private static final long serialVersionUID = 7121406874327830126L;

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
