package org.localstorm.mcc.web.cashflow.actions;

import net.sourceforge.stripes.action.RedirectResolution;
import org.localstorm.mcc.web.ReturnPageBean;
import org.localstorm.mcc.web.util.RedirectUrlBuilderUtil;

/**
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
                rr = RedirectUrlBuilderUtil.redirect(AssetCostHistoryActionBean.class);
                break;
            case BALANCE_HISTORY:
                rr = RedirectUrlBuilderUtil.redirect(RoiReportActionBean.class);
                break;
            case NET_WEALTH_HISTORY:
                rr = RedirectUrlBuilderUtil.redirect(NetWorthHisotryReportActionBean.class);
                break;
            case DEBT_HISTORY:
                rr = RedirectUrlBuilderUtil.redirect(DebtHisotryReportActionBean.class);
                break;
            case OPS_HISTORY:
                rr = RedirectUrlBuilderUtil.redirect(OperationsViewActionBean.class);
                break;
            default:
                rr = NextDestinationUtil.getDefaultRedirection();
                break;
        }

        rpb.appendParams(rr);
        return rr;
    }

    public static RedirectResolution getDefaultRedirection() {
          return RedirectUrlBuilderUtil.redirect(AssetsViewActionBean.class);
    }
}
