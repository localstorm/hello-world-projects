package org.localstorm.mcc.web.cashflow.actions.wrap;

import java.util.ArrayList;
import java.util.Collection;
import org.localstorm.mcc.ejb.cashflow.asset.Asset;
import org.localstorm.mcc.ejb.cashflow.asset.AssetManager;
import org.localstorm.mcc.ejb.cashflow.asset.Target;
import org.localstorm.mcc.ejb.cashflow.asset.ValuableObject;

/**
 *
 * @author localstorm
 */
public class WrapUtil {

    public static Asset wrapAsset(Asset ass, 
                                  AssetManager am)
    {
        ValuableObject vo = ass.getValuable();

        return new AssetWrapper(ass, 
                                am.getTotalAmount(vo),
                                am.getCurrentCost(vo),
                                am.getInvestmentsCost(vo),
                                am.getBalance(vo),
                                am.getRevenuAmount(vo));
    }

    public static Collection<Asset> wrapAssets(Collection<Asset> assets,
                                               AssetManager am)
    {
        Collection<Asset> result = new ArrayList<Asset>(assets.size());
        for (Asset ass: assets)
        {
            result.add(wrapAsset(ass, am));
        }

        return result;
    }

     public static Target wrapTarget(Target tgt,
                                  AssetManager am)
    {
        ValuableObject vo = tgt.getValuable();
        return new TargetWrapper(tgt, am.getCurrentCost(vo));
    }

    public static Collection<Target> wrapTargets(Collection<Target> targets,
                                               AssetManager am)
    {
        Collection<Target> result = new ArrayList<Target>(targets.size());
        for (Target tgt: targets)
        {
            result.add(wrapTarget(tgt, am));
        }

        return result;
    }
}
