/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.localstorm.mcc.ejb.lists.impl;

import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.Query;
import org.localstorm.mcc.ejb.AbstractManager;
import org.localstorm.mcc.ejb.contexts.Context;
import org.localstorm.mcc.ejb.lists.GTDList;
import org.localstorm.mcc.ejb.lists.ListManagerLocal;
import org.localstorm.mcc.ejb.lists.ListManagerRemote;
import java.util.*;
/**
 *
 * @author localstorm
 */
@Stateless
public class ListManagerBean extends AbstractManager<GTDList>
                             implements ListManagerLocal, ListManagerRemote 
{
    public ListManagerBean() {
        super(GTDList.class);
    }

    
    @Override
    public Collection<GTDList> findByContext(Context ctx)
    {
        Query lq = em.createNamedQuery(GTDList.Queries.FIND_BY_CTX);
        lq.setParameter(GTDList.Properties.CONTEXT, ctx);
        
        List<GTDList> list = lq.getResultList();
        System.out.println("RETURNED: "+list.size());
        return list;
    }

    @Override
    public Collection<GTDList> findByContextArchived(Context ctx) {
        Query lq = em.createNamedQuery(GTDList.Queries.FIND_BY_CTX_ARCHIVED);
        lq.setParameter(GTDList.Properties.CONTEXT, ctx);
        
        List<GTDList> list = lq.getResultList();
        System.out.println("RETURNED: "+list.size());
        return list;
    }
    
}
