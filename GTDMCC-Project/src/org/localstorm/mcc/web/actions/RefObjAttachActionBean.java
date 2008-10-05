package org.localstorm.mcc.web.actions;

import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.notes.Note;
import org.localstorm.mcc.ejb.referenced.ReferencedObject;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/AttachRefObj")
public class RefObjAttachActionBean extends RefObjViewActionBean
{
    @Validate( required=true )
    private int objectId;
    
    @Validate( required=true )
    private String text;
    
    @Validate( required=true )
    private String description;

    @After( LifecycleStage.BindingAndValidation ) 
    public void doPostValidationStuff() throws Exception {
        if ( getContext().getValidationErrors().hasFieldErrors() )
        {
            getContext().getRequest().setAttribute("urlForm", "true");
            super.filling();
        }
    }
    
    @Override
    public int getObjectId() {
        return objectId;
    }

    @Override
    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setText(String text) {
        this.text = text;
    }

    @DefaultHandler
    @Override
    public Resolution filling() throws Exception {
        
        ReferencedObject ro = this.getRefObjectManager().findById(this.objectId);
        
        Note note = new Note(text, "URL");
        note.setDescription(description);
        this.getNoteManager().createAttachedNote(note, ro);
        
        RedirectResolution rr = new RedirectResolution(RefObjViewActionBean.class);
        {
            rr.addParameter(RefObjViewActionBean.IncommingParameters.OBJECT_ID, this.objectId);
        }
        return rr;
    }

    public static interface IncommingParameters {
        public static final String OBJECT_ID = "objectId";
    }
      
}
