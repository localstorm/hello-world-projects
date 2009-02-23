package org.localstorm.mcc.ejb.cashflow.asset;

import java.util.Collection;
import org.localstorm.mcc.ejb.users.User;


/**
 *
 * @author localstorm
 */

public interface AssetManager
{
    public static final String BEAN_NAME="AssetManagerBean";
    
    public Collection<Asset> findArchivedAssetsByOwner(User user);

    public Asset findAssetByValuable(ValuableObject vo);
    
    public Collection<Asset> findAssetsByOwner(User user);

    public Asset findAssetById(int assetId);

    public void create(Asset newAsset, Cost assetCost);

    public void remove(Asset asset);

    public void update(Asset asset);
    
}
