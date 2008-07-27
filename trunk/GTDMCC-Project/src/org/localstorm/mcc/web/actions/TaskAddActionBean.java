package org.localstorm.mcc.web.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/AddTask")
public class TaskAddActionBean extends BaseActionBean
{

    @Validate( required=true )
    private String summary;
    
    @Validate( required=true )
    private int listId;

    public int getListId() {
        return listId;
    }

    public String getSummary() {
        return summary;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    // TODO: Type

    /*@After( LifecycleStage.BindingAndValidation ) 
    public void doPostValidationStuff() {
        if ( getContext().getValidationErrors().hasFieldErrors() )
        {
            System.out.println("Forced Filling contextlist");
            super.filling();
        }
    }*/
    
    
   
    @DefaultHandler
    public Resolution addList() {
        System.out.println("Adding task:"+getSummary());

        ForwardResolution fr = new ForwardResolution( ListViewActionBean.class );
        fr.addParameter( "id", listId );
        return fr;
    }
    
}
