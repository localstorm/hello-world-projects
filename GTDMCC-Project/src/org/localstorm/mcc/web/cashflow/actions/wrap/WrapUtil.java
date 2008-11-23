package org.localstorm.mcc.web.cashflow.actions.wrap;

import java.util.ArrayList;
import java.util.Collection;
import org.localstorm.mcc.ejb.cashflow.Asset;
import org.localstorm.mcc.ejb.cashflow.AssetManager;
import org.localstorm.mcc.ejb.cashflow.ValuableObject;

/**
 *
 * @author localstorm
 */
public class WrapUtil {

    public static Collection<Asset> wrapAssets(Collection<Asset> assets,
                                               AssetManager am)
    {
        Collection<Asset> result = new ArrayList<Asset>(assets.size());
        for (Asset ass: assets)
        {
            ValuableObject vo = ass.getValuable();
            result.add(new AssetWrapper(ass,
                                        am.getTotalAmount(vo),
                                        am.getCurrentCost(vo),
                                        am.getInvestmentsCost(vo)));
        }

        return result;
    }
}
