package org.localstorm.mcc.ejb;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Alexey Kuznetsov
 */
public abstract class AbstractSingletonManager <T extends Identifiable, E> 
                                               implements BaseSingletonManager<T, E>
{
    protected abstract T create(E owner);

    @Override
    public void update( T o ) 
    {
        em.merge( o );
    }

    @Override
    public void utilizeCurrent( E e ) {
        T current = this.findByUser(e);
        em.remove(current);
        em.flush();
        this.create( e );
    }
    
    
    @PersistenceContext(unitName=Constants.DEFAULT_PU)
    protected EntityManager em;
    
}
