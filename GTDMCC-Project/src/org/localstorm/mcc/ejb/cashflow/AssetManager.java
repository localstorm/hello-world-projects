package org.localstorm.mcc.ejb.cashflow;

import java.math.BigDecimal;
import java.util.Collection;
import org.localstorm.mcc.ejb.users.User;


/**
 *
 * @author localstorm
 */

public interface AssetManager
{
    public static final String BEAN_NAME="AssetManagerBean";
    
    public void createAsset(Asset newAsset, Cost assetCost);
        
    public void createTarget(Target newTarget, Cost targetCost);

    public Collection<Asset> findAssetsByOwner(User user);

    public BigDecimal getNetWealthSellCost(ValuableObject vo);

    public BigDecimal getInvestmentsCost(ValuableObject vo);

    public BigDecimal getTotalAmount(ValuableObject vo);

    public Cost getCurrentCost(ValuableObject vo);

    public void updateCost(ValuableObject vo, Cost cost);

    // Operations

    public void buy(ValuableObject vo, BigDecimal amount, String comment, boolean exchange);

    public boolean sell(ValuableObject vo, BigDecimal amount, String comment, boolean exchange);

    public Collection<Operation> getOperations(ValuableObject vo);
}
