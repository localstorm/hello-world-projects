package org.localstorm.jmx.checker;

import javax.tools.JavaCompiler;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;

/**
 * Created by IntelliJ IDEA.
 * User: aakuznetsov
 * Date: 14.07.11
 * Time: 15:43
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationInfo implements Serializable {
    private String host;
    private String id;

    public ApplicationInfo() {
        this.host = buildHost();
        this.id = java.util.UUID.randomUUID().toString();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getId() {
        return id;
    }

    public void setId(String guid) {
        this.id = guid;
    }

    public String toString() {
        return "App: (Host=" + host + ", GUID=" + id + ")";
    }

    private String buildHost() {
        try {
            InetAddress addr = InetAddress.getLocalHost();

            // Get IP Address
            byte[] ipAddr = addr.getAddress();

            // Get hostname
            return addr.getHostName();
        } catch (UnknownHostException e) {
            return "Unknown-host";
        }
    }

}
