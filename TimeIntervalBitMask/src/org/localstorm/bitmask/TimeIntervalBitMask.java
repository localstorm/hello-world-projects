package org.localstorm.bitmask;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Alexey Kuznetsov
 */
public class TimeIntervalBitMask
{
    private RotatingBitSet bitMask;
    private int            size;
    private Calendar       right;

    public TimeIntervalBitMask(int sizeInMinutes)
    {
        this.size    = sizeInMinutes;
        this.bitMask = new RotatingBitSet( sizeInMinutes );
        Calendar tmp = Calendar.getInstance(DateTimeUtilities.UTC_TIMEZONE);
        this.right   = Calendar.getInstance(DateTimeUtilities.UTC_TIMEZONE);
        
        this.right.set(tmp.get(Calendar.YEAR),
                       tmp.get(Calendar.MONTH),
                       tmp.get(Calendar.DAY_OF_MONTH),
                       tmp.get(Calendar.HOUR_OF_DAY),
                       tmp.get(Calendar.MINUTE),
                       0);
        this.right.set(Calendar.MILLISECOND, 0);

    }

    /*
       Shifts mask and makes it up-to-date. Returns false if right edge minute is
       less than NOW-sizeInMinutes
     */
    public boolean timeShift()
    {
        // getting difference between now and right
        int diff= (int) ((System.currentTimeMillis() - right.getTimeInMillis()) / 60000);

        right.add(Calendar.MINUTE, diff);
        this.bitMask.rotl(diff);
        
        return this.bitMask.length()>0;

    }

    public void setBits(DateInterval interval)
    {
        this.timeShift();

        if (interval.getRight().before(this.getLeft()) ||
            interval.getLeft().after(this.right.getTime()))
        {
            return;
        }

        int from = (int) ((right.getTimeInMillis() - interval.getLeft().getTime()) / 60000);
        int to   = (int) ((right.getTimeInMillis() - interval.getRight().getTime()) / 60000);

        from = Math.min(size-1, from); // Bull shit
        to   = Math.max(0, to);

        for (int i=to; i<=from; i++)
        {
            this.bitMask.set(i);
        }
    }

    /**
     * Returns <code>true</code> if bit that corresponds to given minute is 1
     * @param minuteDate
     * @return <code>true</code> if bit that corresponds to given minute is 1
     */
    public boolean getBitByMinute(Date minuteDate)
    {
        int point = (int) ((right.getTimeInMillis() - minuteDate.getTime()) / 60000);
        return (point>=0 && point<size && bitMask.get(point));
    }

    @Override
    public String toString()
    {
        return bitMask.toString() + ":" + right.getTime();
    }

    public Date getRight()
    {
        return this.right.getTime();
    }

    public Date getLeft()
    {
        Calendar cal = (Calendar) this.right.clone();
        cal.add(Calendar.MINUTE, -size);
        return cal.getTime();
    }

}
