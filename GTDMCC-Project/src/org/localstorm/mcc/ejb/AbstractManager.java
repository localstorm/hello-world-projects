package org.localstorm.mcc.ejb;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.localstorm.mcc.ejb.except.DuplicateException;
import org.localstorm.mcc.ejb.except.ObjectNotFoundException;


/**
 *
 * @author Alexey Kuznetsov
 */
public abstract class AbstractManager<T extends Identifiable> implements BaseManager<T>
{

    public AbstractManager(Class<T> c) {
        this.cl = c;
    }
    
    @Override
    public void update( T o ) 
    {
        em.merge( o );
    }
    
    
    @Override
    public T create(T o) throws DuplicateException
    {
        try 
        {
            em.persist(o);
            em.flush();
            return o;
        } catch(EntityExistsException e) 
        {
            throw new DuplicateException(e);
        }
    }
    
    @Override
    public T findById( int id ) throws ObjectNotFoundException
    {
        T t = em.find(cl, id);
        if (t==null)
        {
            throw new ObjectNotFoundException();
        }
        
        return t;
    }

    @Override
    public void flush() {
        em.flush();
    }

    @Override
    public void remove(T obj) {
        obj = em.find(cl, obj.getId() );
        em.remove(obj);
    }
    
    @PersistenceContext(unitName=Constants.DEFAULT_PU)
    protected EntityManager em;
    
    protected Class<T> cl;
}
