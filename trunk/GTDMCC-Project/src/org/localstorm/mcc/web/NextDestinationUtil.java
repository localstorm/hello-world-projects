package org.localstorm.mcc.web;

import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import org.localstorm.mcc.ejb.lists.GTDList;
import org.localstorm.mcc.ejb.tasks.TaskResolutionAction;
import org.localstorm.mcc.web.actions.ContextViewActionBean;
import org.localstorm.mcc.web.actions.ListViewActionBean;

/**
 *
 * @author Alexey Kuznetsov
 */
public class NextDestinationUtil 
{
    public static Resolution taskResolveActionResolution(TaskResolutionAction action, 
                                                         GTDList list, 
                                                         GTDList currentList)
    {
        RedirectResolution rr = null;

        if (list.isArchived() && (!TaskResolutionAction.REMOVE.equals(action))) {
            rr = new RedirectResolution(ContextViewActionBean.class);
            {
                rr.addParameter(ContextViewActionBean.IncommingParameters.CTX_ID, list.getContext().getId());
            }
        } else {
            rr = new RedirectResolution(ListViewActionBean.class);
            {
                if (currentList!=null)
                {
                    Integer id = currentList.getId();
                    rr.addParameter(ListViewActionBean.IncommingParameters.LIST_ID, id);
                } else {
                    rr.addParameter(ListViewActionBean.IncommingParameters.LIST_ID, list.getId());
                }
            }
        }

        return rr;
    }
}
