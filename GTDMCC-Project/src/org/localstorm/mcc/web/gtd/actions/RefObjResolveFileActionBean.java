package org.localstorm.mcc.web.gtd.actions;

import org.localstorm.mcc.web.gtd.GtdBaseActionBean;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.gtd.files.FileAttachment;
import org.localstorm.mcc.ejb.gtd.files.FileManager;
import org.localstorm.mcc.ejb.gtd.referenced.RefObjectManager;
import org.localstorm.mcc.ejb.gtd.referenced.ReferencedObject;
import org.localstorm.mcc.web.gtd.GtdClipboard;

/**
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/gtd/ctx/obj/file/ResolveRefObjFile")
public class RefObjResolveFileActionBean extends GtdBaseActionBean
{
    @Validate( required=true )
    private String action;

    @Validate( required=true )
    private Integer objectId;

    @Validate( required=true )
    private Integer fileId;

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
   
    public Integer getFileId()
    {
        return fileId;
    }

    public void setFileId(Integer fileId)
    {
        this.fileId = fileId;
    }
    
    @DefaultHandler
    public Resolution handling() throws Exception
    {
        Action      a  = Action.valueOf(this.getAction());
        
        RefObjectManager rom = super.getRefObjectManager();
        FileManager fm = super.getFileManager();
        GtdClipboard clip = super.getClipboard();

        FileAttachment   fa = null;
        ReferencedObject ro = rom.findById(this.getObjectId());
        
        switch (a)
        {
            case CUT_FILE:
                fa = fm.findById(this.getFileId());
                clip.copyFile(fa);
                break;
            case PASTE_FILES:
                for (FileAttachment att : clip.getFiles()) {
                    fm.reattach(att, ro);
                }
                clip.clearFiles();
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
        public static final String FILE_ID       = "fileId";
        public static final String ACTION        = "action";
    }

    private static enum Action
    {
        CUT_FILE,
        PASTE_FILES
    }
    
}
