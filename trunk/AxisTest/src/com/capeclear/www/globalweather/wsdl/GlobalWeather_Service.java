/**
 * GlobalWeather_Service.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.capeclear.www.globalweather.wsdl;

public interface GlobalWeather_Service extends javax.xml.rpc.Service {
    public java.lang.String getStationInfoAddress();

    public com.capeclear.www.globalweather.wsdl.StationInfo getStationInfo() throws javax.xml.rpc.ServiceException;

    public com.capeclear.www.globalweather.wsdl.StationInfo getStationInfo(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getGlobalWeatherAddress();

    public com.capeclear.www.globalweather.wsdl.GlobalWeather_PortType getGlobalWeather() throws javax.xml.rpc.ServiceException;

    public com.capeclear.www.globalweather.wsdl.GlobalWeather_PortType getGlobalWeather(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
