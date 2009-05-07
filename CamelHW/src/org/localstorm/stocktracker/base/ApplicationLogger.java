package org.localstorm.stocktracker.base;

/**
 *
 * @author Alexey Kuznetsov
 */
public class ApplicationLogger
{
    private final static ApplicationLogger instance = new ApplicationLogger();

    private ApplicationLogger() {

    }

    public static ApplicationLogger getInstance() {
        return instance;
    }

    public void log(String message) {
        System.out.println(message);
        System.out.flush();
    }

    public void log(String message, Exception e) {
        System.out.println(message+e.getMessage());
        System.out.flush();
    }


}
