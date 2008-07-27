package org.localstorm.mcc.web.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

import org.localstorm.mcc.ejb.contexts.Context;
import org.localstorm.mcc.ejb.lists.GTDList;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ViewList")
public class ListViewActionBean extends BaseActionBean
{
    @Validate( required=true )
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    private GTDList listResult;

    public GTDList getListResult() {
        return listResult;
    }

    public void setListResult(GTDList listResult) {
        this.listResult = listResult;
    }

    
    @DefaultHandler
    public Resolution filling() {
        System.out.println("Viewing list:" +id);
        return new ForwardResolution("/jsp/viewList.jsp");
    }
}
