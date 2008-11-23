package org.localstorm.mcc.ejb.cashflow;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
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
    public void createAsset(Asset newAsset, Cost assetCost) {
       em.persist(newAsset);
       assetCost.setValuable(newAsset.getValuable());
       em.persist(assetCost);
    }

    @Override
    public void createTarget(Target newTarget, Cost targetCost) {
       em.persist(newTarget);
       targetCost.setValuable(newTarget.getValuable());
       em.persist(targetCost);
    }

    @Override
    public Collection<Asset> findAssetsByOwner(User user) {
        Query uq = em.createNamedQuery(Asset.Queries.FIND_BY_OWNER);
        uq.setParameter(Asset.Properties.OWNER, user);

        List<Asset> list = uq.getResultList();
        return list;
    }

    @Override
    public Cost getCurrentCost(ValuableObject vo) {
        Query uq = em.createNamedQuery(Cost.Queries.FIND_COSTS_BY_VO_DESC);
        uq.setParameter(Cost.Properties.VALUABLE, vo);
        uq.setMaxResults(1);

        Cost cost = (Cost) uq.getSingleResult();
        return cost;
    }

    @Override
    public BigDecimal getNetWealthSellCost(ValuableObject vo) {
        BigDecimal amount = this.getTotalAmount(vo);
        Cost curCost = this.getCurrentCost(vo);
        return curCost.getSell().multiply(amount);
    }

    @Override
    public void updateCost(ValuableObject vo, Cost cost) {
        cost.setActuationDate(new Date());
        cost.setValuable(vo);
        em.persist(cost);
    }

    @Override
    public void buy(ValuableObject vo, BigDecimal amount, String comment, boolean exchange) {
        Operation op = new Operation();
        {
            op.setType((!exchange)?OperationTypes.BUY:OperationTypes.BUY_FX);
            op.setAmount(amount);
            op.setCost(this.getCurrentCost(vo));
            op.setOperationDate(new Date());
            op.setComment(comment);
        }
        
        em.persist(op);
    }

    @Override
    public boolean sell(ValuableObject vo, BigDecimal amount, String comment, boolean exchange) {
        
        if ( this.getTotalAmount(vo).compareTo(amount)<0 ) {
            return false;
        }

        Operation op = new Operation();
        {
            op.setType((!exchange)?OperationTypes.SELL:OperationTypes.SELL_FX);
            op.setAmount(amount.negate());
            op.setCost(this.getCurrentCost(vo));
            op.setOperationDate(new Date());
            op.setComment(comment);
        }

        em.persist(op);
        return true;
    }

   @Override
    public Collection<Operation> getOperations(ValuableObject vo) {
        Query uq = em.createNamedQuery(Operation.Queries.FIND_BY_VO_DESC);
        uq.setParameter(Cost.Properties.VALUABLE, vo);

        Collection<Operation> ops = (Collection<Operation>) uq.getResultList();
        return ops;
    }

    @Override
    public BigDecimal getInvestmentsCost(ValuableObject vo) {
        Query b1 = em.createNamedQuery(Operation.Queries.SUM_BOUGHT_BY_VO);
        b1.setParameter(Cost.Properties.VALUABLE, vo);

        Query b2 = em.createNamedQuery(Operation.Queries.SUM_BOUGHT_FOR_EXCHANGE_BY_VO);
        b2.setParameter(Cost.Properties.VALUABLE, vo);

        BigDecimal s1 = this.nvl((BigDecimal) b1.getSingleResult());
        BigDecimal s2 = this.nvl((BigDecimal) b2.getSingleResult());

        return s1.add(s2);
    }

    @Override
    public BigDecimal getTotalAmount(ValuableObject vo) {
        Query total = em.createNamedQuery(Operation.Queries.SUM_AMOUNT_BY_VO);
        total.setParameter(Cost.Properties.VALUABLE, vo);

        BigDecimal sum = this.nvl((BigDecimal) total.getSingleResult());
        return sum;
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
