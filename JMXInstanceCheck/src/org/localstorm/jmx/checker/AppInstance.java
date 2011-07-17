package org.localstorm.jmx.checker;

/**
 * Created by IntelliJ IDEA.
 * User: aakuznetsov
 * Date: 14.07.11
 * Time: 15:38
 * To change this template use File | Settings | File Templates.
 */
public class AppInstance implements AppInstanceMBean {

    private final static ApplicationInfo info =  new ApplicationInfo();

    public ApplicationInfo echo() {
        return info;
    }
}
