package org.localstorm.mcc.ejb.cashflow.stat;

import java.util.Collection;
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
public class HistoricalValuesManagerBean implements HistoricalValuesManagerLocal,
                                         HistoricalValuesManagerRemote
{

    @Override
    public Collection<HistoricalValue> findByValueTag(String valueTag, User user) {
        Query s1 = em.createNamedQuery(HistoricalValue.Queries.FIND_BY_VALUE_TAG);
        s1.setParameter(HistoricalValue.Properties.VALUE_TAG, valueTag);
        s1.setParameter(HistoricalValue.Properties.OWNER, user);

        return (Collection<HistoricalValue>) s1.getResultList();
    }

    @Override
    public Collection<HistoricalValue> findByValueTagAndObjectId(String valueTag, Integer objectId, User user) {
        Query s1 = em.createNamedQuery(HistoricalValue.Queries.FIND_BY_VALUE_AND_OBJECT_ID_TAG);
        s1.setParameter(HistoricalValue.Properties.VALUE_TAG, valueTag);
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
