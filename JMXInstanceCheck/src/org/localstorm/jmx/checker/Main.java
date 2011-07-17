package org.localstorm.jmx.checker;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.io.FileInputStream;
import java.io.FileReader;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: aakuznetsov
 * Date: 14.07.11
 * Time: 15:39
 * To change this template use File | Settings | File Templates.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        // Get the Platform MBean Server
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

        // Construct the ObjectName for the MBean we will register
        ObjectName name = new ObjectName("com.example.mbeans:type=AppInstance");

        // Create the Hello World MBean
        AppInstance mbean = new AppInstance();

        // Register the Hello World MBean
        mbs.registerMBean(mbean, name);

        List<String> jmxEndpoints = IOUtils.readLines(new FileReader("jmx.txt"));

        AppInstanceFinder finder = new AppInstanceFinder(jmxEndpoints);

        for (;;) {
            Collection<ApplicationInfo> info = finder.checkNeighborhood();
            for (ApplicationInfo i : info) {
                System.out.println(i);
            }
            Thread.sleep(1000);
        }
    }

}
