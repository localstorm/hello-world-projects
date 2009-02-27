package org.localstorm.mcc.ejb.gtd.contexts;

import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import org.localstorm.mcc.ejb.AbstractManager;
import org.localstorm.mcc.ejb.users.User;

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
    public Collection<Context> find(User u)
    {
        Query uq = em.createNamedQuery(Context.Queries.FIND_BY_OWNER);
        uq.setParameter(Context.Properties.OWNER, u);
        
        List<Context> list = uq.getResultList();
        return list;
    }

    @Override
    public List<Context> findArchived(User u) {
        Query uq = em.createNamedQuery(Context.Queries.FIND_BY_OWNER_ARCHIVED);
        uq.setParameter(Context.Properties.OWNER, u); // 
        
        List<Context> list = uq.getResultList();
        return list;
    }
    
    
   
}
