package org.localstorm.bitmask;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Alexey Kuznetsov
 */
public class TimelineCache
{
    private Map<Integer, CacheEntry> store;
    private int tbmSizeInMinutes;


    public TimelineCache(int tbmSizeInMinutes) {
        store = new HashMap<Integer, CacheEntry>();
        this.tbmSizeInMinutes = tbmSizeInMinutes;
    }

    public void put( Integer apsId, boolean state ) {
        TimeIntervalBitMask tbm = new TimeIntervalBitMask(tbmSizeInMinutes);
        CacheEntry ce = new CacheEntry(apsId, tbm, state);
        store.put(apsId, ce);
    }

    public CacheEntry get( Integer apsId )
    {
        return store.get(apsId);
    }

    public void clearJunkEntries()
    {
        for (Map.Entry<Integer, CacheEntry> entry : store.entrySet())
        {
            if (!entry.getValue().getTbm().timeShift()) {
                store.remove(entry.getKey());
            }
        }
    }

}
