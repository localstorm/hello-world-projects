package org.localstorm.bitmask;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Alexey Kuznetsov
 */
public class TimelineCache
{
    private Map<Integer, CacheSegment> store;
    private int tbmSizeInMinutes;


    public TimelineCache(int tbmSizeInMinutes) {
        store = new HashMap<Integer, CacheSegment>();
        this.tbmSizeInMinutes = tbmSizeInMinutes;
    }

    public void putCacheSegment( Integer apsId, CacheSegment cs ) {
        store.put(apsId, cs);
    }

    public CacheSegment getCacheSegment( Integer apsId, boolean createIfNone )
    {
        CacheSegment cs = store.get(apsId);
        
        if (createIfNone && cs==null)
        {
            cs = new CacheSegment(this.tbmSizeInMinutes);
            store.put(apsId, cs);
        }

        return cs;
    }

    public void clearJunkEntries()
    {
        for (Iterator<Map.Entry<Integer, CacheSegment>> it = store.entrySet().iterator(); it.hasNext(); )
        {
            Map.Entry<Integer, CacheSegment> entry = it.next();
            if (!entry.getValue().timeShift())
            {
                it.remove(); // Iterator?
            }
        }
    }

   
}
