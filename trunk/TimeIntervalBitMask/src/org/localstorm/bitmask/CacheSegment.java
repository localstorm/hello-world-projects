package org.localstorm.bitmask;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Alexey Kuznetsov
 */
public class CacheSegment 
{
    private Map<Integer, CacheEntry> entries = new TreeMap<Integer, CacheEntry>();
    private int tbmSizeInMinutes;

    CacheSegment(int tbmSizeInMinutes)
    {
        this.tbmSizeInMinutes = tbmSizeInMinutes;
    }

    public CacheEntry getCacheEntry(Integer psrId, boolean createIfNone)
    {
        CacheEntry ce = entries.get(psrId);
        
        if (createIfNone && ce==null) 
        {
            ce = new CacheEntry(psrId, new TimeIntervalBitMask(this.tbmSizeInMinutes), true);
            entries.put(psrId, ce);
        }

        return ce;
    }

    public void putCacheEntry(CacheEntry ce)
    {
        entries.put(ce.getResourceId(), ce);
    }

    public boolean timeShift()
    {
        for (Iterator<Map.Entry<Integer, CacheEntry>> it = entries.entrySet().iterator(); it.hasNext(); )
        {
            Map.Entry<Integer, CacheEntry> ce = it.next();
            if (!ce.getValue().getTbm().timeShift())
            {
                it.remove();
            }
        }

        return !(entries.isEmpty());
    }
}
