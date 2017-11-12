package org.localstorm.mcc.web.gtd.actions;

import net.sourceforge.stripes.action.RedirectResolution;
import org.localstorm.mcc.web.ReturnPageBean;
import org.localstorm.mcc.web.dashboard.actions.DashboardActionBean;
import org.localstorm.mcc.web.util.RedirectUrlBuilderUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
public class NextDestinationUtil 
{

    public static RedirectResolution getRedirection(ReturnPageBean rpb) {
        if (rpb==null) {
            return NextDestinationUtil.getDefaultRedirection();
        }

        Pages rp = Pages.valueOf(rpb.getPageToken());

        RedirectResolution rr;
        switch(rp)
        {
            case LIST_VIEW:
                rr = RedirectUrlBuilderUtil.redirect(ListViewActionBean.class);
                break;
            case TASK_SEARCH_SUBMIT:
                rr = RedirectUrlBuilderUtil.redirect(TaskSearchSubmitActionBean.class);
                break;
            case BMS_VIEW:
                rr = RedirectUrlBuilderUtil.redirect(BattleMapSupportActionBean.class);
                break;
            case DL_REPORT:
                rr = RedirectUrlBuilderUtil.redirect(DeadlineReportActionBean.class);
                break;
            case OLD_REPORT:
                rr = RedirectUrlBuilderUtil.redirect(OldTasksReportActionBean.class);
                break;
            case DASH:
                rr = RedirectUrlBuilderUtil.redirect(DashboardActionBean.class);
                break;
            case FPV:
                rr = RedirectUrlBuilderUtil.redirect(FlightPlanViewActionBean.class);
                break;
            default:
                rr = NextDestinationUtil.getDefaultRedirection();
                break;
        }

        rpb.appendParams(rr);
        return rr;
    }

    public static RedirectResolution getDefaultRedirection() {
          return RedirectUrlBuilderUtil.redirect(FlightPlanViewActionBean.class);
    }
}
