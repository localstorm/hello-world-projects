package org.localstorm.stocktracker;

/**
 *
 * @author Alexey Kuznetsov
 */
public interface Endpoints
{
    public static final String TRACKING_XML_HANDLER_URI = "txml:singleton";
    public static final String TRACKING_SCHEDULER_URI   = "sched:singleton";
    public static final String INSTRUCTOR_URI           = "i:singleton";
    public static final String STOCK_ANALYZER_URI       = "a:singleton";
    public static final String NOTIFIER_URI             = "n:singleton";
}
