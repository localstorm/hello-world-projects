package org.localstorm.mcc.web.gtd.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.gtd.referenced.RefObjectManager;
import org.localstorm.mcc.ejb.gtd.referenced.ReferencedObject;
import org.localstorm.mcc.web.SessionKeys;
import org.localstorm.mcc.web.util.SessionUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ToggleStateRefObj")
public class RefObjToggleStateActionBean extends BaseActionBean
{
    @Validate( required=true )
    private int objectId;

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    
    @DefaultHandler
    public Resolution toggle() throws Exception {
        
        System.out.println("Deleting/Undeleting referenced object:" +objectId);
        
        RefObjectManager rom = super.getRefObjectManager();
        ReferencedObject ro = rom.findById(this.getObjectId());
        ro.setArchived( !ro.isArchived() );
        rom.update(ro);
        
        SessionUtil.clear(getSession(), SessionKeys.REFERENCE_OBJECTS);
        return new RedirectResolution(RefObjEditActionBean.class);
    }
}