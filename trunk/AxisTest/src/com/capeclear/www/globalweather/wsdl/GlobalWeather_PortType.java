/**
 * GlobalWeather_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.capeclear.www.globalweather.wsdl;

public interface GlobalWeather_PortType extends java.rmi.Remote {
    public com.capeclear.www.globalweather.xsd.WeatherReport getWeatherReport(java.lang.String code) throws java.rmi.RemoteException;
}
