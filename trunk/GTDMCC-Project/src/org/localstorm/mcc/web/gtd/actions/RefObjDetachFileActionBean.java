package org.localstorm.mcc.web.gtd.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.gtd.files.FileAttachment;
import org.localstorm.mcc.ejb.gtd.files.FileManager;
import org.localstorm.mcc.ejb.gtd.referenced.ReferencedObject;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/DetachFileRefObj")
public class RefObjDetachFileActionBean extends BaseActionBean
{
    @Validate( required=true )
    private int objectId;
    
    @Validate( required=true )
    private int fileId;
    
    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }


    @DefaultHandler
    public Resolution handling() throws Exception {
        
        FileManager fm = this.getFileManager();
        ReferencedObject ro = this.getRefObjectManager().findById(this.objectId);
        
        FileAttachment fa = fm.findAttachmentById(this.getFileId());
        fm.detach(fa, ro);
        
        RedirectResolution rr = new RedirectResolution(RefObjViewActionBean.class);
        {
            rr.addParameter(RefObjViewActionBean.IncommingParameters.OBJECT_ID, this.objectId);
        }
        return rr;
    }

    public static interface IncommingParameters {
        public static final String OBJECT_ID = "objectId";
        public static final String FILE_ID = "fileId";
    }
    
}