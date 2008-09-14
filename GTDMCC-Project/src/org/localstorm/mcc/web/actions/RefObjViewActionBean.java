package org.localstorm.mcc.web.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

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

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public ReferencedObject getObjectResult() {
        return objectResult;
    }

    @DefaultHandler
    public Resolution filling() throws Exception {
        
        this.objectResult = super.getRefObjectManager().findById(this.getObjectId());
        
        super.setCurrent(objectResult.getContext());
        
        return new ForwardResolution(Views.VIEW_RO);
    }

    public static interface IncommingParameters {
        public static final String OBJECT_ID = "objectId";
    }
    
}
