package org.localstorm.mcc.ejb.gtd.impl;

import org.localstorm.mcc.ejb.AbstractManager;
import org.localstorm.mcc.ejb.gtd.entity.Context;
import org.localstorm.mcc.ejb.users.User;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

/**
 * @author localstorm
 */
@Stateless
public class ContextManagerBean extends AbstractManager<Context>
                                implements ContextManagerLocal
{

    public ContextManagerBean() {
        super(Context.class);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public Collection<Context> getContexts(User u)
    {
        Query uq = em.createNamedQuery(Context.Queries.FIND_BY_OWNER);
        uq.setParameter(Context.Properties.OWNER, u);

        return (List<Context>) uq.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Context> getArchived(User u) {
        Query uq = em.createNamedQuery(Context.Queries.FIND_BY_OWNER_ARCHIVED);
        uq.setParameter(Context.Properties.OWNER, u);

        return (List<Context>) uq.getResultList();
    }
    
    
   
}
