/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.localstorm.mcc.ejb;

/**
 *
 * @author Alexey Kuznetsov
 */
public interface BaseSingletonManager<T, E> 
{
    public void update( T o );
    
    public T findByUser( E owner );
    
    public void utilizeCurrent( E owner );
}
