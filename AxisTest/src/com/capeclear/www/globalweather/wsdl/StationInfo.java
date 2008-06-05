/**
 * StationInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.capeclear.www.globalweather.wsdl;

public interface StationInfo extends java.rmi.Remote {
    public com.capeclear.www.globalweather.xsd.Station getStation(java.lang.String code) throws java.rmi.RemoteException;
    public boolean isValidCode(java.lang.String code) throws java.rmi.RemoteException;
    public java.lang.String[] listCountries() throws java.rmi.RemoteException;
    public com.capeclear.www.globalweather.xsd.Station[] searchByCode(java.lang.String code) throws java.rmi.RemoteException;
    public com.capeclear.www.globalweather.xsd.Station[] searchByCountry(java.lang.String country) throws java.rmi.RemoteException;
    public com.capeclear.www.globalweather.xsd.Station[] searchByLocation(com.capeclear.www.globalweather.xsd.Range latitude, com.capeclear.www.globalweather.xsd.Range longitude, com.capeclear.www.globalweather.xsd.Range elevation) throws java.rmi.RemoteException;
    public com.capeclear.www.globalweather.xsd.Station[] searchByName(java.lang.String name) throws java.rmi.RemoteException;
    public com.capeclear.www.globalweather.xsd.Station[] searchByRegion(java.lang.String region) throws java.rmi.RemoteException;
}
