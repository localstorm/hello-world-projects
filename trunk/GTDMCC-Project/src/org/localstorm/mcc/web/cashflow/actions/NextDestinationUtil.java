package org.localstorm.mcc.web.cashflow.actions;

import net.sourceforge.stripes.action.RedirectResolution;
import org.localstorm.mcc.web.ReturnPageBean;

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
            case ASSET_COST_HISTORY:
                rr = new RedirectResolution(AssetCostHistoryActionBean.class);
                break;
            case BALANCE_HISTORY:
                rr = new RedirectResolution(BalanceHisotryReportActionBean.class);
                break;
            case NET_WEALTH_HISTORY:
                rr = new RedirectResolution(NetWealthHisotryReportActionBean.class);
                break;
            default:
                rr = NextDestinationUtil.getDefaultRedirection();
                break;
        }

        rpb.appendParams(rr);
        return rr;
    }

    public static RedirectResolution getDefaultRedirection() {
          return new RedirectResolution(AssetsViewActionBean.class);
    }
}
