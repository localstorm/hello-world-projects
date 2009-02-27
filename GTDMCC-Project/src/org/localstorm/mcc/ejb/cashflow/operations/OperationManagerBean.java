package org.localstorm.mcc.ejb.cashflow.operations;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.localstorm.mcc.ejb.Constants;
import org.localstorm.mcc.ejb.cashflow.assets.Cost;
import org.localstorm.mcc.ejb.cashflow.assets.ValuableObject;
import org.localstorm.mcc.ejb.except.ObjectNotFoundException;

/**
 *
 * @author localstorm
 */
@Stateless
public class OperationManagerBean implements OperationManagerLocal
{

    public OperationManagerBean() {

    }

    @Override
    public void remove(Operation op) {
        op = em.getReference(Operation.class, op.getId());
        em.remove(op);
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
    public Cost getCurrentCost(ValuableObject vo) {
        Query uq = em.createNamedQuery(Cost.Queries.FIND_COSTS_BY_VO_DESC);
        {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, -1000);

            uq.setParameter(Cost.Properties.VALUABLE, vo);
            uq.setParameter(Cost.Properties.MIN_DATE, cal.getTime());
            uq.setMaxResults(1);
        }

        Cost cost = (Cost) uq.getSingleResult();
        return cost;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Cost> getCostHistory(ValuableObject vo, Date minDate) {
        Query uq = em.createNamedQuery(Cost.Queries.FIND_COSTS_BY_VO_DESC);
        uq.setParameter(Cost.Properties.VALUABLE, vo);
        uq.setParameter(Cost.Properties.MIN_DATE, minDate);
        return (Collection<Cost>) uq.getResultList();
    }

    @Override
    public void buy(ValuableObject vo, BigDecimal amount, String comment, boolean exchange) {
        Operation op = new Operation();
        {
            OperationType type = (!exchange)?OperationType.BUY:OperationType.BUY_FX;
            op.setType(type.toString());
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
            OperationType type = (!exchange)?OperationType.SELL:OperationType.SELL_FX;
            op.setType(type.toString());
            op.setAmount(amount.negate());
            op.setCost(this.getCurrentCost(vo));
            op.setOperationDate(new Date());
            op.setComment(comment);
        }

        em.persist(op);
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
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

        BigDecimal sb1 = this.nvl((BigDecimal) b1.getSingleResult());
        BigDecimal sb2 = this.nvl((BigDecimal) b2.getSingleResult());

        return sb1.add(sb2);
    }

    @Override
    public BigDecimal getRevenuAmount(ValuableObject vo) {
        Query s1 = em.createNamedQuery(Operation.Queries.SUM_SELL_BY_VO);
        s1.setParameter(Cost.Properties.VALUABLE, vo);

        Query s2 = em.createNamedQuery(Operation.Queries.SUM_SELL_FOR_EXCHANGE_BY_VO);
        s2.setParameter(Cost.Properties.VALUABLE, vo);

        BigDecimal ss1 = this.nvl((BigDecimal) s1.getSingleResult());
        BigDecimal ss2 = this.nvl((BigDecimal) s2.getSingleResult());
        return BigDecimal.ZERO.subtract(ss1).subtract(ss2);
    }

    @Override
    public BigDecimal getBalance(ValuableObject vo) {

        BigDecimal netWealth  = this.getNetWealthSellCost(vo);
        BigDecimal investment = this.getInvestmentsCost(vo);
        BigDecimal ro         = this.getRevenuAmount(vo);

        return netWealth.add(ro).subtract(investment);
    }


    @Override
    public BigDecimal getTotalAmount(ValuableObject vo) {
        Query total = em.createNamedQuery(Operation.Queries.SUM_AMOUNT_BY_VO);
        total.setParameter(Cost.Properties.VALUABLE, vo);

        BigDecimal sum = this.nvl((BigDecimal) total.getSingleResult());
        return sum;
    }

    @Override
    public Operation findOperation(Integer operationId) throws ObjectNotFoundException {
        Operation o = em.find(Operation.class, operationId);

        if (o==null) {
            throw new ObjectNotFoundException();
        }

        return o;
    }

    private BigDecimal nvl(BigDecimal bigDecimal) {
        if (bigDecimal==null) {
            return new BigDecimal(0.0);
        }
        return bigDecimal;
    }

    @Override
    public ValuableObject findValuable(Integer valuableId) throws ObjectNotFoundException {
        ValuableObject vo = em.find(ValuableObject.class, valuableId);

        if (vo==null) {
            throw new ObjectNotFoundException();
        }

        return vo;
    }

    @Override
    public void update(ValuableObject vo) {
        em.merge( vo );
    }

    @PersistenceContext(unitName=Constants.DEFAULT_PU)
    protected EntityManager em;
}
