package org.localstorm.jmx.checker;

import java.util.ArrayList;
import java.util.Collection;
import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: aakuznetsov
 * Date: 14.07.11
 * Time: 15:40
 * To change this template use File | Settings | File Templates.
 */
public class AppInstanceFinder {

    private List<String> jmxServices;

    public AppInstanceFinder(List<String> jmxServices) {
        this.jmxServices = jmxServices;
    }

    public Collection<ApplicationInfo> checkNeighborhood() throws Exception {
        List<ApplicationInfo> info = new ArrayList<ApplicationInfo>();

        for (String host : jmxServices) {
            try {
                JMXServiceURL url =
                        new JMXServiceURL(host);
                JMXConnector jmxc = JMXConnectorFactory.connect(url, null);

                MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

                ObjectName mbeanName = new ObjectName("com.example.mbeans:type=AppInstance");

                AppInstanceMBean mbeanProxy =
                        JMX.newMBeanProxy(mbsc, mbeanName, AppInstanceMBean.class, true);

                info.add(mbeanProxy.echo());
                jmxc.close();
            } catch (Exception e) {
                //e.printStackTrace();
                System.err.println("No response from ["+host+"]");
            }
        }

        return info;
    }

}
