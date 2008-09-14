package org.localstorm.mcc.web.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/AttachRefObj")
public class RefObjAttachActionBean extends BaseActionBean
{
    @Validate( required=true )
    private int objectId;
    
    @Validate( required=true )
    private String attachmentType;
    
    @Validate( required=false )
    private String text;
    
    @Validate( required=false )
    private String description;
    
    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public void setAttachmentType(String attachmentType) {
        this.attachmentType = attachmentType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setText(String text) {
        this.text = text;
    }

    @DefaultHandler
    public Resolution filling() throws Exception {
        
        RedirectResolution rr = new RedirectResolution(RefObjViewActionBean.class);
        {
            rr.addParameter(RefObjViewActionBean.IncommingParameters.OBJECT_ID, this.objectId);
        }
        return rr;
    }

    public static interface IncommingParameters {
        public static final String OBJECT_ID = "objectId";
    }
    
    private static enum AttachmentTypes
    {
        URL,
        NONE,
        FILE
    }
}
