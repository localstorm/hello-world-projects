package org.localstorm.bitmask;

/**
 *
 * @author Alexey Kuznetsov
 */
public class CacheEntry 
{
    private Integer             psrId;
    private TimeIntervalBitMask tbm;
    private boolean             resumption;

    CacheEntry(Integer psrId, TimeIntervalBitMask tbm, boolean resumption)
    {
        this.resumption = resumption;
        this.psrId = psrId;
        this.tbm   = tbm;
    }

    public Integer getResourceId()
    {
        return psrId;
    }

    public TimeIntervalBitMask getTbm()
    {
        return tbm;
    }

    public void setTbm(TimeIntervalBitMask tbm)
    {
        this.tbm = tbm;
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

