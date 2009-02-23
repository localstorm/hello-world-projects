package org.localstorm.mcc.ejb.cashflow.assets;

import java.util.Collection;
import org.localstorm.mcc.ejb.users.User;


/**
 *
 * @author localstorm
 */

public interface AssetManager
{
    public static final String BEAN_NAME="AssetManagerBean";
    
    public Collection<Asset> findArchivedAssets(User user);

    public Asset findAssetByValuable(ValuableObject vo);
    
    public Collection<Asset> findAssets(User user);

    public Asset findById(int assetId);

    public void create(Asset newAsset, Cost assetCost);

    public void remove(Asset asset);

    public void update(Asset asset);
    
}
