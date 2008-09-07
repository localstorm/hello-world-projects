package org.localstorm.mcc.ejb.referenced.impl;

import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import org.localstorm.mcc.ejb.AbstractManager;
import org.localstorm.mcc.ejb.contexts.Context;
import org.localstorm.mcc.ejb.referenced.RefObjectManagerLocal;
import org.localstorm.mcc.ejb.referenced.RefObjectManagerRemote;
import org.localstorm.mcc.ejb.referenced.ReferencedObject;
import org.localstorm.mcc.ejb.users.User;

/**
 *
 * @author Alexey Kuznetsov
 */

/**
 *
 * @author Alexey Kuznetsov
 */
@Stateless
public class RefObjectManagerBean extends AbstractManager<ReferencedObject>
                                  implements RefObjectManagerLocal, RefObjectManagerRemote
{

    public RefObjectManagerBean() {
        super(ReferencedObject.class);
    }
    
    @Override
    public Collection<ReferencedObject> findOperativeByContext(Context ctx) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<ReferencedObject> findOperativeByOwner(User user) {
        Query lq = em.createNamedQuery(ReferencedObject.Queries.FIND_OPERATIVE_BY_OWNER);
        lq.setParameter(ReferencedObject.Properties.OWNER, user);
        
        List<ReferencedObject> list = lq.getResultList();
        System.out.println("RETURNED: "+list.size());
        return list;
    }

    @Override
    public Collection<ReferencedObject> findAllByOwner(User user) {
        Query lq = em.createNamedQuery(ReferencedObject.Queries.FIND_BY_OWNER);
        lq.setParameter(ReferencedObject.Properties.OWNER, user);
        
        List<ReferencedObject> list = lq.getResultList();
        System.out.println("RETURNED: "+list.size());
        return list;
    }
    
    public Collection<ReferencedObject> findAllArchivedByOwner(User user) {
        Query lq = em.createNamedQuery(ReferencedObject.Queries.FIND_ARCHIVED_BY_OWNER);
        lq.setParameter(ReferencedObject.Properties.OWNER, user);
        
        List<ReferencedObject> list = lq.getResultList();
        System.out.println("RETURNED: "+list.size());
        return list;
    }
    

}
