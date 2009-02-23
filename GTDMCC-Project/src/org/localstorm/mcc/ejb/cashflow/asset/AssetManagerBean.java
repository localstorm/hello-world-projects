package org.localstorm.mcc.ejb.cashflow.asset;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.localstorm.mcc.ejb.Constants;
import org.localstorm.mcc.ejb.users.User;

/**
 *
 * @author localstorm
 */
@Stateless
public class AssetManagerBean implements AssetManagerLocal,
                                         AssetManagerRemote
{

    public AssetManagerBean() {

    }

    @Override
    public void create(Asset newAsset, Cost assetCost) {
        ValuableObject vo = newAsset.getValuable();
        em.persist(vo);
        em.persist(newAsset);

        assetCost.setValuable(vo);
        em.persist(assetCost);

        Cost fake = new Cost(vo);
        {
            Calendar cal = Calendar.getInstance();
            cal.setTime(assetCost.getActuationDate());
            cal.add(Calendar.DATE, -1);

            fake.setActuationDate(cal.getTime());
            fake.setBuy(assetCost.getBuy());
            fake.setExchangeBuy(assetCost.getExchangeBuy());
            
            fake.setSell(BigDecimal.ZERO);
            fake.setExchangeSell((assetCost.getExchangeSell()!=null) ? BigDecimal.ZERO : null);
        }
        em.persist(fake);
    }

    @Override
    public void update(Asset asset) {
        em.merge(asset);
    }

    @Override
    public void remove(Asset asset) {
        asset = (Asset) em.getReference(Asset.class, asset.getId() );
        ValuableObject vo = asset.getValuable();
        em.remove(asset);
        em.remove(vo);
    }


    @Override
    public Collection<Asset> findAssetsByOwner(User user) {
        Query uq = em.createNamedQuery(Asset.Queries.FIND_BY_OWNER);
        uq.setParameter(Asset.Properties.OWNER, user);

        List<Asset> list = uq.getResultList();
        return list;
    }

    @Override
    public Collection<Asset> findArchivedAssetsByOwner(User user) {
        Query uq = em.createNamedQuery(Asset.Queries.FIND_ARCHIVED_BY_OWNER);
        uq.setParameter(Asset.Properties.OWNER, user);

        List<Asset> list = uq.getResultList();
        return list;
    }

    @Override
    public Asset findAssetByValuable(ValuableObject vo) {
        Query uq = em.createNamedQuery(Asset.Queries.FIND_BY_VALUABLE);
        uq.setParameter(Asset.Properties.VALUABLE, vo);

        return (Asset) uq.getSingleResult();
    }

    @Override
    public Asset findAssetById(int assetId) {
        return (Asset) em.find(Asset.class, assetId);
    }


    private BigDecimal nvl(BigDecimal bigDecimal) {
        if (bigDecimal==null) {
            return new BigDecimal(0.0);
        }
        return bigDecimal;
    }

    @PersistenceContext(unitName=Constants.DEFAULT_PU)
    protected EntityManager em;
}
