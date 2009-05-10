package org.localstorm.stocktracker.camel;

/**
 *
 * @author Alexey Kuznetsov
 */
public interface Endpoints
{
    public static final String STOCK_PRICES_INPUT_URI        = "direct:stockPrices";
    public static final String TRACKING_REQUESTS_INPUT_URI   = "direct:trackingRequests";
    public static final String TRACKING_XML_HANDLER_URI      = "txml:singleton";
    public static final String TRACKING_SCHEDULER_URI        = "sched:singleton";
    public static final String INSTRUCTOR_URI                = "i:singleton";
    public static final String STOCK_ANALYZER_URI            = "a:singleton";
    public static final String NOTIFIER_URI                  = "n:singleton";
    public static final String PRICES_TRACKER_URI            = "price:singleton";
}
