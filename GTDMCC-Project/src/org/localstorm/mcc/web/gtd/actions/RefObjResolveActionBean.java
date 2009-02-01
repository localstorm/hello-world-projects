package org.localstorm.mcc.web.gtd.actions;

import org.localstorm.mcc.web.gtd.GtdBaseActionBean;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.gtd.files.FileAttachment;
import org.localstorm.mcc.ejb.gtd.files.FileManager;
import org.localstorm.mcc.ejb.gtd.notes.Note;
import org.localstorm.mcc.ejb.gtd.notes.NoteManager;
import org.localstorm.mcc.ejb.gtd.referenced.RefObjectManager;
import org.localstorm.mcc.ejb.gtd.referenced.ReferencedObject;
import org.localstorm.mcc.web.Clipboard;

/**
 * Special checks for instanceId!!!
 * @secure-by object Id parameter
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ResolveRefObj")
public class RefObjResolveActionBean extends GtdBaseActionBean
{
    @Validate( required=true )
    private String action;

    @Validate( required=true )
    private Integer objectId;

    @Validate( required=true )
    private Integer instanceId;

    public String getAction()
    {
        return action;
    }

    public void setAction(String action)
    {
        this.action = action;
    }

    public Integer getObjectId()
    {
        return objectId;
    }

    public void setObjectId(Integer objectId)
    {
        this.objectId = objectId;
    }
   
    public Integer getInstanceId()
    {
        return instanceId;
    }

    public void setInstanceId(Integer instanceId)
    {
        this.instanceId = instanceId;
    }
    
    @DefaultHandler
    public Resolution handling() throws Exception
    {
        Action      a  = Action.valueOf(this.getAction());
        
        RefObjectManager rom = super.getRefObjectManager();
        FileManager fm = super.getFileManager();
        NoteManager nm = super.getNoteManager();
        Clipboard clip = super.getClipboard();

        FileAttachment   fa = null;
        Note             no = null;
        ReferencedObject ro = rom.findById(this.getObjectId());
        
        switch (a)
        {
            case CUT_FILE:
                fa = fm.findById(this.getInstanceId());
                clip.copyFile(fa);
                break;
            case CUT_NOTE:
                no = nm.findById(this.getInstanceId());
                clip.copyNote(no);
                break;
            case PASTE_FILES:
                for (FileAttachment att : clip.getFiles()) {
                    fm.reattach(att, ro);
                }
                clip.clearFiles();
                break;
            case PASTE_NOTES:
                for (Note note : clip.getNotes()) {
                    nm.reattach(note, ro);
                }
                clip.clearNotes();
                break;
            default:
                throw new RuntimeException("Unexpected case!");
        }

        RedirectResolution rr = new RedirectResolution(RefObjViewActionBean.class);
        {
            rr.addParameter(RefObjViewActionBean.IncommingParameters.OBJECT_ID, this.getObjectId());
        }

        return rr;
    }

    public static interface IncommingParameters {
        public static final String OBJECT_ID     = "destId";
        public static final String INSTANCE_ID   = "instanceId";
        public static final String ACTION        = "action";
    }

    private static enum Action
    {
        CUT_NOTE,
        CUT_FILE,
        PASTE_NOTES,
        PASTE_FILES
    }
    
}
