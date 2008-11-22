package org.localstorm.mcc.ejb;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Alexey Kuznetsov
 */
public abstract class AbstractSingletonManager <T extends Identifiable & Retireable, E> 
                                               implements BaseSingletonManager<T, E>
{
    protected abstract T createNewCurrent(E owner);

    @Override
    public void update( T o ) 
    {
        em.merge( o );
    }

    @Override
    public void utilizeCurrent( E e ) {
        T current = this.findCurrent(e);
        current.setArchived(true);
        this.update(current);
        this.createNewCurrent( e );
    }
    
    
    @PersistenceContext(unitName=Constants.DEFAULT_PU)
    protected EntityManager em;
    
}
