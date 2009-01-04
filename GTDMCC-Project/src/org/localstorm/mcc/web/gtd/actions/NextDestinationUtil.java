package org.localstorm.mcc.web.gtd.actions;

import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import org.localstorm.mcc.ejb.gtd.lists.GTDList;
import org.localstorm.mcc.ejb.gtd.tasks.TaskResolutionAction;
import org.localstorm.mcc.web.ReturnPageBean;

/**
 *
 * @author Alexey Kuznetsov
 */
public class NextDestinationUtil 
{

//    public static Resolution getRedirectionByReturnPageName(String returnPage)
//    {
//        ReturnPages rp = ReturnPages.valueOf(returnPage);
//
//        switch(rp)
//        {
//            case AW_REPORT:
//                return new RedirectResolution(AwaitingsReportActionBean.class);
//            case DL_REPORT:
//                return new RedirectResolution(DeadlineLookupReportActionBean.class);
//            case EASY_REPORT:
//                return new RedirectResolution(EasyTasksReportActionBean.class);
//            case OLD_REPORT:
//                return new RedirectResolution(OldTasksReportActionBean.class);
//            default:
//            case FPV:
//                return new RedirectResolution(FlightPlanViewActionBean.class);
//
//        }
//    }

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

    public static RedirectResolution getRedirection(ReturnPageBean rpb) {
        if (rpb==null) {
            return NextDestinationUtil.getDefaultRedirection();
        }

        Pages rp = Pages.valueOf(rpb.getPageToken());

        RedirectResolution rr;
        switch(rp)
        {
            case BMS_VIEW:
                rr = new RedirectResolution(BattleMapSupportActionBean.class);
                break;
            case DL_REPORT:
                rr = new RedirectResolution(DeadlineLookupReportActionBean.class);
                break;
            case EASY_REPORT:
                rr = new RedirectResolution(EasyTasksReportActionBean.class);
                break;
            case OLD_REPORT:
                rr = new RedirectResolution(OldTasksReportActionBean.class);
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
