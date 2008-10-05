package org.localstorm.mcc.ejb.files.impl;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.localstorm.mcc.ejb.Identifiable;
import org.localstorm.mcc.ejb.files.FileAttachment;
import org.localstorm.mcc.ejb.referenced.ReferencedObject;

/**
 *
 * @author Alexey Kuznetsov
 */
@Entity
@Table(name="FILES_TO_OBJECTS")
public class FileToRefObject implements Serializable, Identifiable
{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "file_id")
    private FileAttachment file;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "object_id")
    private ReferencedObject refObject;

    public FileToRefObject() {
    }

    public FileToRefObject(FileAttachment fa, ReferencedObject refObject) {
        this.id        = null;
        this.file      = fa;
        this.refObject = refObject;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    public ReferencedObject getRefObject() {
        return refObject;
    }

    public FileAttachment getFile() {
        return file;
    }

    public void setRefObject(ReferencedObject refObject) {
        this.refObject = refObject;
    }

    public void setFile(FileAttachment file) {
        this.file = file;
    }
    
}
