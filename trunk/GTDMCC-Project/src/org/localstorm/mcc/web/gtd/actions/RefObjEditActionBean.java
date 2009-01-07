package org.localstorm.mcc.web.gtd.actions;

import org.localstorm.mcc.web.gtd.GtdBaseActionBean;
import java.util.Collection;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.ejb.gtd.referenced.RefObjectManager;
import org.localstorm.mcc.ejb.gtd.referenced.ReferencedObject;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.gtd.Views;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/EditRefObj")
public class RefObjEditActionBean extends GtdBaseActionBean {

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
        RefObjectManager rom = super.getRefObjectManager();
        User user = super.getUser();
        
        this.setRefObjects(     rom.findOperativeByOwner(user, true) );
        this.setArchiveObjects( rom.findAllArchivedByOwner(user) );
        return new ForwardResolution( Views.EDIT_RO );
    }

}
