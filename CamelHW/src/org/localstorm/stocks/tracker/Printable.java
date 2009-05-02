package org.localstorm.stocks.tracker;

import org.apache.commons.beanutils.BeanUtils;

/**
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
