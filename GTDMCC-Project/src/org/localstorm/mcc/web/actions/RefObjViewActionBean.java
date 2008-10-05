package org.localstorm.mcc.web.actions;

import java.util.Collection;
import java.util.List;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

import org.localstorm.mcc.ejb.files.FileAttachment;
import org.localstorm.mcc.ejb.files.FileManager;
import org.localstorm.mcc.ejb.notes.Note;
import org.localstorm.mcc.ejb.referenced.ReferencedObject;
import org.localstorm.mcc.web.Views;


/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ViewRefObj")
public class RefObjViewActionBean extends BaseActionBean
{
    @Validate( required=true )
    private int objectId;
    
    private ReferencedObject objectResult;
    private Collection<Note> objectNotes;
    private Collection<FileAttachment> objectFiles;

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public ReferencedObject getObjectResult() {
        return objectResult;
    }

    public Collection<Note> getObjectNotes() {
        return objectNotes;
    }

    public Collection<FileAttachment> getObjectFiles() {
        return objectFiles;
    }

    @DefaultHandler
    public Resolution filling() throws Exception {
        
        this.objectResult = super.getRefObjectManager().findById(this.getObjectId());
        this.objectNotes  = super.getNoteManager().findByObject(objectResult);
        this.objectFiles  = super.getFileManager().findAllAttachmentsByObject(objectResult);
        
        super.setCurrent(objectResult.getContext());
        
        return new ForwardResolution(Views.VIEW_RO);
    }

    public static interface IncommingParameters {
        public static final String OBJECT_ID = "objectId";
    }
    
}
