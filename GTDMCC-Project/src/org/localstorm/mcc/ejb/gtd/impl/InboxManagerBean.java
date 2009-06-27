package org.localstorm.mcc.ejb.gtd.impl;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.localstorm.mcc.ejb.Constants;
import org.localstorm.mcc.ejb.gtd.entity.InboxEntry;
import org.localstorm.mcc.ejb.users.User;

/**
 * @author localstorm
 */
@Stateless
public class InboxManagerBean implements InboxManagerLocal
{
    @Override
    @SuppressWarnings("unchecked")
    public List<InboxEntry> getInboxEntries(User owner)
    {
        Query uq = em.createNamedQuery(InboxEntry.Queries.FIND_BY_OWNER);
        uq.setParameter(InboxEntry.Properties.OWNER, owner);

        List<InboxEntry> list = uq.getResultList();
        return list;
    }

    @Override
    public void removeNote(InboxEntry ie)
    {
        InboxEntry toRemove = em.getReference( InboxEntry.class, ie.getId() );
        em.remove(toRemove);
    }

    @Override
    public void submitNote(InboxEntry ie)
    {
        em.persist(ie);
        em.flush();
    }

    @PersistenceContext(unitName=Constants.DEFAULT_PU)
    protected EntityManager em;
}
