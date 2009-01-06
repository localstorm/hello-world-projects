package org.localstorm.mcc.ejb;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.localstorm.mcc.ejb.except.ObjectNotFoundException;

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
        try {
            T current = this.findByUser(e, false);
            em.remove(current);
            em.flush();
        } catch(ObjectNotFoundException ex) {
           /* ignoring */
        }
        this.create( e );
    }
    
    
    @PersistenceContext(unitName=Constants.DEFAULT_PU)
    protected EntityManager em;
    
}
