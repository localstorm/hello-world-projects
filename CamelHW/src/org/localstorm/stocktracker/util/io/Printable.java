package org.localstorm.stocktracker.util.io;

import org.apache.commons.beanutils.BeanUtils;

/**
 * Provides toString() for any JavaBean instances.
 * @author Alexey Kuznetsov
 */
public abstract class Printable
{

    @Override
    public String toString()
    {
        try {
            return BeanUtils.describe(this).toString();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
