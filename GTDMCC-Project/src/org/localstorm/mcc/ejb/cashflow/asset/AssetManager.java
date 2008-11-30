package org.localstorm.mcc.ejb.cashflow.asset;

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

    public Collection<Asset> findArchivedAssetsByOwner(User user);

    public Asset findAssetsByValuable(ValuableObject vo);
    
    public Collection<Asset> findAssetsByOwner(User user);

    public Asset findAssetById(int assetId);

    public void createTarget(Target newTarget, Cost targetCost);

    public BigDecimal getBalance(ValuableObject vo);

    public BigDecimal getNetWealthSellCost(ValuableObject vo);

    public BigDecimal getInvestmentsCost(ValuableObject vo);

    public BigDecimal getTotalAmount(ValuableObject vo);

    public BigDecimal getRevenuAmount(ValuableObject vo);

    public Cost getCurrentCost(ValuableObject vo);

    public void remove(Asset asset);

    public void update(Asset asset);

    public void updateCost(ValuableObject vo, Cost cost);

    // Operations

    public ValuableObject findValuableById(Integer valuableId);

    public void buy(ValuableObject vo, BigDecimal amount, String comment, boolean exchange);

    public boolean sell(ValuableObject vo, BigDecimal amount, String comment, boolean exchange);

    public Collection<Operation> getOperations(ValuableObject vo);

    public void updateValuableObject(ValuableObject vo);
}
