package org.localstorm.bitmask;

import java.util.BitSet;

/**
 *
 * @author Alexey Kuznetsov
 */
public class RotatingBitSet extends BitSet
{
    private static final long serialVersionUID = 1L;

    private int maxLength;

    public RotatingBitSet(int maxLength) {
        super(maxLength);

        if (maxLength<1)
        {
            throw new IllegalArgumentException("Hmm.. maxLength less than 1?");
        }
        
        this.maxLength = maxLength;
    }

    @Override
    public boolean get(int bitIndex)
    {
        if (bitIndex >= maxLength)
        {
            throw new RuntimeException("Index out of bounds ["+bitIndex+"]!");
        }
        return super.get(bitIndex);
    }

    @Override
    public void set(int bitIndex)
    {
        if (bitIndex >= maxLength)
        {
            throw new RuntimeException("Index out of bounds ["+bitIndex+"]!");
        }
        super.set(bitIndex);
    }

    public void rotl(int shift) {
        shift = Math.min(shift, maxLength);
        if (shift==0)
        {
            return;
        }
        
        int index = maxLength-1;
        int i = index - shift;
        for (; i>=0; i--)
        {
            set(index--, get(i));
        }

        for (i=0;i<shift; i++)
        {
            clear(i);
        }
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(maxLength);
        for (int i=maxLength-1; i>=0; i--)
        {
            sb.append( get(i) ? '1' : '0' );
        }
        return sb.toString();
    }



}
