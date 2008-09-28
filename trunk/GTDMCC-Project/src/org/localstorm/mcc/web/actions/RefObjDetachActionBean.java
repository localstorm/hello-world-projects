package org.localstorm.mcc.web.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.notes.Note;
import org.localstorm.mcc.ejb.referenced.ReferencedObject;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/DetachRefObj")
public class RefObjDetachActionBean extends BaseActionBean
{
    @Validate( required=true )
    private int objectId;
    
    @Validate( required=true )
    private int noteId;
    
    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }


    @DefaultHandler
    public Resolution handling() throws Exception {
        
        ReferencedObject ro = this.getRefObjectManager().findById(this.objectId);
        Note note           = this.getNoteManager().findById(this.noteId);
        
        this.getNoteManager().detachNote(note, ro);
        
        RedirectResolution rr = new RedirectResolution(RefObjViewActionBean.class);
        {
            rr.addParameter(RefObjViewActionBean.IncommingParameters.OBJECT_ID, this.objectId);
        }
        return rr;
    }

    public static interface IncommingParameters {
        public static final String OBJECT_ID = "objectId";
        public static final String NOTE_ID = "noteId";
    }
    
}
