package org.localstorm.mcc.web.gtd.actions;

import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.FileBean;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.gtd.files.FileAttachment;
import org.localstorm.mcc.ejb.gtd.files.FileManager;
import org.localstorm.mcc.ejb.gtd.referenced.ReferencedObject;

/**
 * @secure-by object Id parameter
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/UploadRefObj")
public class RefObjUploadActionBean extends RefObjViewActionBean
{
    @Validate( required=true )
    private int objectId;
    
    @Validate( required=true )
    private String description;
    
    @Validate( required=true )
    private FileBean file;

    public FileBean getFile() {
        return file;
    }

    public void setFile(FileBean file) {
        this.file = file;
    }

    @After( LifecycleStage.BindingAndValidation ) 
    public void doPostValidationStuff() throws Exception {
        if ( getContext().getValidationErrors().hasFieldErrors() )
        {
            getContext().getRequest().setAttribute("fileForm", "true");
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

    public String getDescription() {
        return description;
    }

    @DefaultHandler
    @Override
    public Resolution filling() throws Exception {
        
        ReferencedObject ro = super.getRefObjectManager().findById(this.objectId);
        
        FileManager fm      = super.getFileManager();
        FileAttachment fa   = new FileAttachment();
        
        
        FileBean fb = this.getFile();
        fa.setName(fb.getFileName());
        fa.setMimeType(fb.getContentType());
        fa.setDescription(this.getDescription());
                
        fm.attachToObject(fa, ro, fb.getInputStream());
        
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
