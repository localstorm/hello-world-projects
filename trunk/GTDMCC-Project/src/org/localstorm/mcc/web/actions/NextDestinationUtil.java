package org.localstorm.mcc.web.actions;

import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import org.localstorm.mcc.ejb.gtd.lists.GTDList;
import org.localstorm.mcc.ejb.gtd.tasks.TaskResolutionAction;

/**
 *
 * @author Alexey Kuznetsov
 */
public class NextDestinationUtil 
{

    public static Resolution getRedirectionByReturnPageName(String returnPage)
    {
        ReturnPages rp = ReturnPages.valueOf(returnPage);

        switch(rp)
        {
            case AW_REPORT:
                return new RedirectResolution(AwaitingsReportActionBean.class);
            case DL_REPORT:
                return new RedirectResolution(DeadlinesReportActionBean.class);
            case EASY_REPORT:
                return new RedirectResolution(EasyTasksReportActionBean.class);
            default:
            case IDX:
                return new RedirectResolution(IndexActionBean.class);

        }
    }

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
