package org.localstorm.stocktracker.config;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>XML configuration Java binding class
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="trackingRequestMaxSize" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="userMaxTrackingEventsQuota" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="eventMinIntervalSize" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="pricesRequestMaxSize" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="routeBuilderBeanShellScript" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *       &lt;attribute name="host" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="port" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "trackingRequestMaxSize",
    "userMaxTrackingEventsQuota",
    "eventMinIntervalSize",
    "pricesRequestMaxSize",
    "routeBuilderBeanShellScript"
})
@XmlRootElement(name = "stocktracker")
public class Configuration implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected int trackingRequestMaxSize;
    protected int userMaxTrackingEventsQuota;
    protected int eventMinIntervalSize;
    protected int pricesRequestMaxSize;
    @XmlElement(required = true)
    protected String routeBuilderBeanShellScript;
    @XmlAttribute(required = true)
    protected String host;
    @XmlAttribute(required = true)
    protected int port;

    /**
     * Gets the value of the trackingRequestMaxSize property.
     * 
     */
    public int getTrackingRequestMaxSize() {
        return trackingRequestMaxSize;
    }

    /**
     * Sets the value of the trackingRequestMaxSize property.
     * 
     */
    public void setTrackingRequestMaxSize(int value) {
        this.trackingRequestMaxSize = value;
    }

    /**
     * Gets the value of the userMaxTrackingEventsQuota property.
     * 
     */
    public int getUserMaxTrackingEventsQuota() {
        return userMaxTrackingEventsQuota;
    }

    /**
     * Sets the value of the userMaxTrackingEventsQuota property.
     * 
     */
    public void setUserMaxTrackingEventsQuota(int value) {
        this.userMaxTrackingEventsQuota = value;
    }

    /**
     * Gets the value of the eventMinIntervalSize property.
     * 
     */
    public int getEventMinIntervalSize() {
        return eventMinIntervalSize;
    }

    /**
     * Sets the value of the eventMinIntervalSize property.
     * 
     */
    public void setEventMinIntervalSize(int value) {
        this.eventMinIntervalSize = value;
    }

    /**
     * Gets the value of the pricesRequestMaxSize property.
     * 
     */
    public int getPricesRequestMaxSize() {
        return pricesRequestMaxSize;
    }

    /**
     * Sets the value of the pricesRequestMaxSize property.
     * 
     */
    public void setPricesRequestMaxSize(int value) {
        this.pricesRequestMaxSize = value;
    }

    /**
     * Gets the value of the routeBuilderBeanShellScript property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRouteBuilderBeanShellScript() {
        return routeBuilderBeanShellScript;
    }

    /**
     * Sets the value of the routeBuilderBeanShellScript property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRouteBuilderBeanShellScript(String value) {
        this.routeBuilderBeanShellScript = value;
    }

    /**
     * Gets the value of the host property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHost() {
        return host;
    }

    /**
     * Sets the value of the host property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHost(String value) {
        this.host = value;
    }

    /**
     * Gets the value of the port property.
     */
    public int getPort() {
        return port;
    }

    /**
     * Sets the value of the port property.
     */
    public void setPort(int value) {
        this.port = value;
    }

}
