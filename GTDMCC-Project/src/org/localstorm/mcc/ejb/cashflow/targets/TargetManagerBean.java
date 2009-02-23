package org.localstorm.mcc.ejb.cashflow.targets;

import org.localstorm.mcc.ejb.cashflow.asset.*;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.localstorm.mcc.ejb.Constants;
import org.localstorm.mcc.ejb.except.ObjectNotFoundException;
import org.localstorm.mcc.ejb.users.User;

/**
 *
 * @author localstorm
 */
@Stateless
public class TargetManagerBean implements TargetManagerLocal,
                                          TargetManagerRemote
{
   
    @Override
    public void create(Target newTarget, Cost targetCost) {
       ValuableObject vo = newTarget.getValuable();
       em.persist(vo);
       em.persist(newTarget);

       targetCost.setValuable(vo);
       em.persist(targetCost);
    }

    @Override
    public void remove(Target target) {
        target = em.getReference(Target.class, target.getId());
        em.remove(target);
    }

    @Override
    public void update(Target target) {
        em.merge(target);
    }

    @Override
    public Collection<Target> findTargetsByOwner(User user) {
        Query uq = em.createNamedQuery(Target.Queries.FIND_BY_OWNER);
        uq.setParameter(Asset.Properties.OWNER, user);

        List<Target> list = uq.getResultList();
        return list;
    }

    @Override
    public Target findTargetById(int targetId) throws ObjectNotFoundException {
        Target t = em.find(Target.class, targetId);
        if (t==null)
        {
            throw new ObjectNotFoundException();
        }

        return t;
    }



    @PersistenceContext(unitName=Constants.DEFAULT_PU)
    protected EntityManager em;
}
