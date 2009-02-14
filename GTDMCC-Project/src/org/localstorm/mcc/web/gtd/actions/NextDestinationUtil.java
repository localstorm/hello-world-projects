package org.localstorm.mcc.web.gtd.actions;

import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import org.localstorm.mcc.ejb.gtd.lists.GTDList;
import org.localstorm.mcc.ejb.gtd.tasks.TaskResolutionAction;
import org.localstorm.mcc.web.ReturnPageBean;
import org.localstorm.mcc.web.dashboard.actions.DashboardActionBean;

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

        if (list.isArchived()) {
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

    public static RedirectResolution getRedirection(ReturnPageBean rpb) {
        if (rpb==null) {
            return NextDestinationUtil.getDefaultRedirection();
        }

        Pages rp = Pages.valueOf(rpb.getPageToken());

        RedirectResolution rr;
        switch(rp)
        {
            case TASK_SEARCH_SUBMIT:
                rr = new RedirectResolution(TaskSearchSubmitActionBean.class);
                break;
            case BMS_VIEW:
                rr = new RedirectResolution(BattleMapSupportActionBean.class);
                break;
            case DL_REPORT:
                rr = new RedirectResolution(DeadlineReportActionBean.class);
                break;
            case OLD_REPORT:
                rr = new RedirectResolution(OldTasksReportActionBean.class);
                break;
            case DASH:
                rr = new RedirectResolution(DashboardActionBean.class);
                break;
            case FPV:
                rr = new RedirectResolution(FlightPlanViewActionBean.class);
                break;
            default:
                rr = NextDestinationUtil.getDefaultRedirection();
                break;
        }

        rpb.appendParams(rr);
        return rr;
    }

    public static RedirectResolution getDefaultRedirection() {
          return new RedirectResolution(FlightPlanViewActionBean.class);
    }
}
