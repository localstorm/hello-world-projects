package org.localstorm.mcc.ejb.cashflow.stat;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.localstorm.mcc.ejb.Constants;
import org.localstorm.mcc.ejb.users.User;

/**
 *
 * @author localstorm
 */
@Stateless
public class HistoricalValuesManagerBean implements HistoricalValuesManagerLocal,
                                         HistoricalValuesManagerRemote
{

    @Override
    public HistoricalValue findLastByValueTag(ValueType vt, BigDecimal defaultValue, User user) {
        try
        {
            Query s1 = em.createNamedQuery(HistoricalValue.Queries.FIND_BY_VALUE_TAG);
            s1.setParameter(HistoricalValue.Properties.VALUE_TAG, vt.toString());
            s1.setParameter(HistoricalValue.Properties.OWNER, user);
            s1.setMaxResults(1);

            return (HistoricalValue) s1.getSingleResult();
        } catch(NoResultException e) {
            HistoricalValue hv = new HistoricalValue();
            {
                hv.setFixDate(new Date());
                hv.setOwner(user);
                hv.setObjectId(null);
                hv.setValueTag(vt);
                hv.setVal(defaultValue);
            }
            return hv;
        }
    }


    @Override
    public Collection<HistoricalValue> findByValueTag(ValueType valueTag, User user) {
        Query s1 = em.createNamedQuery(HistoricalValue.Queries.FIND_BY_VALUE_TAG);
        s1.setParameter(HistoricalValue.Properties.VALUE_TAG, valueTag.toString());
        s1.setParameter(HistoricalValue.Properties.OWNER, user);

        return (Collection<HistoricalValue>) s1.getResultList();
    }

    @Override
    public Collection<HistoricalValue> findByValueTagAndObjectId(ValueType valueTag, Integer objectId, User user) {
        Query s1 = em.createNamedQuery(HistoricalValue.Queries.FIND_BY_VALUE_AND_OBJECT_ID_TAG);
        s1.setParameter(HistoricalValue.Properties.VALUE_TAG, valueTag.toString());
        s1.setParameter(HistoricalValue.Properties.OWNER, user);
        s1.setParameter(HistoricalValue.Properties.OBJECT_ID, objectId);

        return (Collection<HistoricalValue>) s1.getResultList();
    }

    @Override
    public void log(HistoricalValue hv) {
        em.persist(hv);
    }

    @PersistenceContext(unitName=Constants.DEFAULT_PU)
    protected EntityManager em;
}
