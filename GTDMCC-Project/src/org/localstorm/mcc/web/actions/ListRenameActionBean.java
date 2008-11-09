package org.localstorm.mcc.web.actions;

import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.lists.GTDList;
import org.localstorm.mcc.ejb.lists.ListManager;


/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/RenameList")
public class ListRenameActionBean extends ListViewActionBean
{
    @Validate( required=true )
    private String name;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @After( LifecycleStage.BindingAndValidation ) 
    public void doPostValidationStuff() throws Exception {
        this.getContext().getRequest().setAttribute("renameForm", Boolean.TRUE);
        
        if ( this.getContext().getValidationErrors().hasFieldErrors() )
        {
            super.filling();
        }
    }

    @DefaultHandler
    @Override
    public Resolution filling() throws Exception {
        ListManager lm = this.getListManager();
        
        GTDList list   = lm.findById(super.getListId());
        list.setName(this.getName());
        lm.update(list);
        
        RedirectResolution rr = new RedirectResolution(ListViewActionBean.class);
        {
            rr.addParameter(ListViewActionBean.IncommingParameters.LIST_ID, super.getListId());
        }
        return rr;
    }
    
    public static interface IncommingParameters {
        public static final String LIST_ID = "listId";
    }
    
}