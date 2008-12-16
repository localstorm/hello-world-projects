package org.localstorm.bitmask;

/**
 *
 * @author Alexey Kuznetsov
 */
public class CacheEntry 
{
    private Integer             apsId;
    private TimeIntervalBitMask tbm;
    private boolean             resumption;

    CacheEntry(Integer apsId, TimeIntervalBitMask tbm, boolean resumption)
    {
        this.tbm = tbm;
        this.resumption = resumption;
        this.apsId = apsId;
    }

    public Integer getApsId()
    {
        return apsId;
    }

    public TimeIntervalBitMask getTbm()
    {
        return tbm;
    }

    public boolean isResumption()
    {
        return resumption;
    }

    public void setResumption(boolean resumption)
    {
        this.resumption = resumption;
    }
    

}

