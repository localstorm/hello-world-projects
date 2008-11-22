package org.localstorm.mcc.web.actions;

import java.util.Collection;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.ejb.gtd.referenced.ReferencedObject;
import org.localstorm.mcc.web.Views;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/EditRefObj")
public class RefObjEditActionBean extends BaseActionBean {

    private Collection<ReferencedObject> refObjects;
    private Collection<ReferencedObject> archiveObjects;
    
    public Collection<ReferencedObject> getRefObjects() {
        return refObjects;
    }

    public void setRefObjects(Collection<ReferencedObject> refObjects) {
        this.refObjects = refObjects;
    }

    public Collection<ReferencedObject> getArchiveObjects() {
        return archiveObjects;
    }

    public void setArchiveObjects(Collection<ReferencedObject> archiveObjects) {
        this.archiveObjects = archiveObjects;
    }
        
    
    @DefaultHandler
    public Resolution filling() {
        this.setRefObjects( super.getRefObjectManager().findOperativeByOwner(super.getUser()) );
        this.setArchiveObjects( super.getRefObjectManager().findAllArchivedByOwner(super.getUser()) );
        return new ForwardResolution( Views.EDIT_RO );
    }

}
