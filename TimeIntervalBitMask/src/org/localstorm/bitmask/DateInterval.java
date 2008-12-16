package org.localstorm.bitmask;

import java.util.Date;

/**
 * @author Alexey Kuznetsov
 */
public class DateInterval 
{
    private Date left;
    private Date right;

    public DateInterval(Date left, Date right)
    {
        this.left = left;
        this.right = right;
    }

    
    public Date getLeft()
    {
        return left;
    }

    public Date getRight()
    {
        return right;
    }

    public void setLeft(Date left)
    {
        this.left = left;
    }

    public void setRight(Date right)
    {
        this.right = right;
    }
    
}
