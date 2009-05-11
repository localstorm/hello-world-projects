package org.localstorm.stocktracker.camel.util;

import org.apache.camel.Producer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Utility class to make producers operations less complex
 * @author Alexey Kuznetsov
 */
public class ProducerUtil 
{
    private static final Log log = LogFactory.getLog(ProducerUtil.class);

    public static void stopQuietly(Producer<?> p ) {
        try {
            p.stop();
        } catch(Exception e) {
            log.error(e);
        }
    }
}
