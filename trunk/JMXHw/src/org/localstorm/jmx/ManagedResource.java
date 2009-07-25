package org.localstorm.jmx;

import java.lang.management.ManagementFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 *
 * @author localstorm
 */
public class ManagedResource extends Thread {

    private AtomicBoolean needStop = new AtomicBoolean(false);
    private Management mrb;

    public ManagedResource() {
        this.mrb = new Management(needStop);
    }

    @Override
    public void run() {
        registerMBean();
        try {
            int i = 0;
            while(!needStop.get()) {
                System.out.println("Working... [" + (i++) + "]");
                mrb.incrementLoopCount();
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted...");
        } finally {
            unregisterMbean();
        }
    }

    private String generateMBeanName() {
        return this.getClass().getPackage().getName() + ":type=" + this.getClass().getSimpleName();
    }

    private void registerMBean() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName(generateMBeanName());
            mbs.registerMBean(mrb, name);
        } catch (JMException e) {
            throw new RuntimeException(e);
        }
    }

    private void unregisterMbean() {

        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName(generateMBeanName());
            mbs.unregisterMBean(name);
        } catch (JMException e) {
            throw new RuntimeException(e);
        }
    }
}
