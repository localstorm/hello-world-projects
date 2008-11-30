/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.localstorm.mcc.web.cashflow.actions;

import net.sourceforge.stripes.action.RedirectResolution;
import org.localstorm.mcc.ejb.cashflow.asset.Asset;
import org.localstorm.mcc.ejb.cashflow.asset.AssetManager;
import org.localstorm.mcc.ejb.cashflow.asset.ValuableObject;

/**
 *
 * @author localstorm
 */
public class NextDestinationUtil {
    public static RedirectResolution getViewByValuableObject(ValuableObject vo, AssetManager am)
    {
        Asset asset = am.findAssetsByValuable(vo);
        if (asset!=null)
        {
//            RedirectResolution rr = new RedirectResolution(AssetViewActionBean.class);
//            {
//                rr.addParameter(AssetViewActionBean.IncommingParameters.ASSET_ID, asset.getId());
//            }
//            return rr;
            return new RedirectResolution(ViewAssetsActionBean.class);
        }

        throw new RuntimeException();
    }
}
