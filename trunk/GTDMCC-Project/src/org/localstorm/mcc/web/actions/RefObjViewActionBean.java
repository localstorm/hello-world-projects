package org.localstorm.mcc.web.actions;

import java.util.Collection;
import java.util.LinkedList;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

import org.localstorm.mcc.ejb.files.FileAttachment;
import org.localstorm.mcc.ejb.notes.Note;
import org.localstorm.mcc.ejb.referenced.ReferencedObject;
import org.localstorm.mcc.web.Types;
import org.localstorm.mcc.web.Views;
import org.localstorm.mcc.web.actions.wrap.WrapUtil;


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
    private Collection<? extends Note> objectTextualNotes;
    private Collection<? extends Note> objectUrlNotes;
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

    public Collection<? extends Note> getObjectUrlNotes() {
        return objectUrlNotes;
    }

    public Collection<? extends Note> getObjectTextualNotes() {
        return objectTextualNotes;
    }

    
    public Collection<FileAttachment> getObjectFiles() {
        return objectFiles;
    }

    @DefaultHandler
    public Resolution filling() throws Exception {
        
        this.objectResult = super.getRefObjectManager().findById(this.getObjectId());
        Collection<Note> urlNotes     = new LinkedList<Note>();
        Collection<Note> textualNotes = new LinkedList<Note>();
        
        this.objectFiles  = super.getFileManager().findAllAttachmentsByObject(objectResult);
        Collection<? extends Note> notes = super.getNoteManager().findByObject(objectResult);
        
        for (Note note: notes) {
            if (Types.TXT.equalsIgnoreCase(note.getType()))
            {
                textualNotes.add(note);
            }
            if (Types.URL.equalsIgnoreCase(note.getType()))
            {
                urlNotes.add(note);
            }
        }
        
        this.objectTextualNotes = WrapUtil.genWrappers(textualNotes);;
        this.objectUrlNotes     = WrapUtil.genWrappers(urlNotes);
        
        super.setCurrent(objectResult.getContext());
        
        return new ForwardResolution(Views.VIEW_RO);
    }

    public static interface IncommingParameters {
        public static final String OBJECT_ID = "objectId";
    }
    
}
