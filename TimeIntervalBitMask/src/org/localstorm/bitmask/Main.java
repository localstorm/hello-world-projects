
package org.localstorm.bitmask;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author localstorm
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        
        TimelineCache tlc = new TimelineCache(5);
        tlc.put(1, false);
        CacheEntry ce = tlc.get(1);

        TimeIntervalBitMask tbm = ce.getTbm();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        cal.add(Calendar.MINUTE, 3);
        Date right = cal.getTime();
        cal.add(Calendar.MINUTE, -8);
        Date left = cal.getTime();

        DateInterval trivial = new DateInterval(left, right);

        tbm.setBits(trivial);

        System.out.println("Z:" + ce.getTbm().toString());

        while (ce.getTbm().timeShift()) {
            
            System.out.println(ce.getTbm().toString());
            tlc.clearJunkEntries();
            Thread.sleep(1000);
        }

        tlc.clearJunkEntries();
        tlc.clearJunkEntries();

        if (tlc.get(1)!=null)
        {
            throw new RuntimeException();
        }
    }

}
