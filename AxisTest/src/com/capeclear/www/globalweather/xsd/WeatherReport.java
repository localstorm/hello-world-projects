/**
 * WeatherReport.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.capeclear.www.globalweather.xsd;

public class WeatherReport  implements java.io.Serializable {
    private java.util.Calendar timestamp;

    private com.capeclear.www.globalweather.xsd.Station station;

    private com.capeclear.www.globalweather.xsd.Phenomenon[] phenomena;

    private com.capeclear.www.globalweather.xsd.Precipitation[] precipitation;

    private com.capeclear.www.globalweather.xsd.Extreme[] extremes;

    private com.capeclear.www.globalweather.xsd.Pressure pressure;

    private com.capeclear.www.globalweather.xsd.Sky sky;

    private com.capeclear.www.globalweather.xsd.Temperature temperature;

    private com.capeclear.www.globalweather.xsd.Visibility visibility;

    private com.capeclear.www.globalweather.xsd.Wind wind;

    public WeatherReport() {
    }

    public WeatherReport(
           java.util.Calendar timestamp,
           com.capeclear.www.globalweather.xsd.Station station,
           com.capeclear.www.globalweather.xsd.Phenomenon[] phenomena,
           com.capeclear.www.globalweather.xsd.Precipitation[] precipitation,
           com.capeclear.www.globalweather.xsd.Extreme[] extremes,
           com.capeclear.www.globalweather.xsd.Pressure pressure,
           com.capeclear.www.globalweather.xsd.Sky sky,
           com.capeclear.www.globalweather.xsd.Temperature temperature,
           com.capeclear.www.globalweather.xsd.Visibility visibility,
           com.capeclear.www.globalweather.xsd.Wind wind) {
           this.timestamp = timestamp;
           this.station = station;
           this.phenomena = phenomena;
           this.precipitation = precipitation;
           this.extremes = extremes;
           this.pressure = pressure;
           this.sky = sky;
           this.temperature = temperature;
           this.visibility = visibility;
           this.wind = wind;
    }


    /**
     * Gets the timestamp value for this WeatherReport.
     * 
     * @return timestamp
     */
    public java.util.Calendar getTimestamp() {
        return timestamp;
    }


    /**
     * Sets the timestamp value for this WeatherReport.
     * 
     * @param timestamp
     */
    public void setTimestamp(java.util.Calendar timestamp) {
        this.timestamp = timestamp;
    }


    /**
     * Gets the station value for this WeatherReport.
     * 
     * @return station
     */
    public com.capeclear.www.globalweather.xsd.Station getStation() {
        return station;
    }


    /**
     * Sets the station value for this WeatherReport.
     * 
     * @param station
     */
    public void setStation(com.capeclear.www.globalweather.xsd.Station station) {
        this.station = station;
    }


    /**
     * Gets the phenomena value for this WeatherReport.
     * 
     * @return phenomena
     */
    public com.capeclear.www.globalweather.xsd.Phenomenon[] getPhenomena() {
        return phenomena;
    }


    /**
     * Sets the phenomena value for this WeatherReport.
     * 
     * @param phenomena
     */
    public void setPhenomena(com.capeclear.www.globalweather.xsd.Phenomenon[] phenomena) {
        this.phenomena = phenomena;
    }


    /**
     * Gets the precipitation value for this WeatherReport.
     * 
     * @return precipitation
     */
    public com.capeclear.www.globalweather.xsd.Precipitation[] getPrecipitation() {
        return precipitation;
    }


    /**
     * Sets the precipitation value for this WeatherReport.
     * 
     * @param precipitation
     */
    public void setPrecipitation(com.capeclear.www.globalweather.xsd.Precipitation[] precipitation) {
        this.precipitation = precipitation;
    }


    /**
     * Gets the extremes value for this WeatherReport.
     * 
     * @return extremes
     */
    public com.capeclear.www.globalweather.xsd.Extreme[] getExtremes() {
        return extremes;
    }


    /**
     * Sets the extremes value for this WeatherReport.
     * 
     * @param extremes
     */
    public void setExtremes(com.capeclear.www.globalweather.xsd.Extreme[] extremes) {
        this.extremes = extremes;
    }


    /**
     * Gets the pressure value for this WeatherReport.
     * 
     * @return pressure
     */
    public com.capeclear.www.globalweather.xsd.Pressure getPressure() {
        return pressure;
    }


    /**
     * Sets the pressure value for this WeatherReport.
     * 
     * @param pressure
     */
    public void setPressure(com.capeclear.www.globalweather.xsd.Pressure pressure) {
        this.pressure = pressure;
    }


    /**
     * Gets the sky value for this WeatherReport.
     * 
     * @return sky
     */
    public com.capeclear.www.globalweather.xsd.Sky getSky() {
        return sky;
    }


    /**
     * Sets the sky value for this WeatherReport.
     * 
     * @param sky
     */
    public void setSky(com.capeclear.www.globalweather.xsd.Sky sky) {
        this.sky = sky;
    }


    /**
     * Gets the temperature value for this WeatherReport.
     * 
     * @return temperature
     */
    public com.capeclear.www.globalweather.xsd.Temperature getTemperature() {
        return temperature;
    }


    /**
     * Sets the temperature value for this WeatherReport.
     * 
     * @param temperature
     */
    public void setTemperature(com.capeclear.www.globalweather.xsd.Temperature temperature) {
        this.temperature = temperature;
    }


    /**
     * Gets the visibility value for this WeatherReport.
     * 
     * @return visibility
     */
    public com.capeclear.www.globalweather.xsd.Visibility getVisibility() {
        return visibility;
    }


    /**
     * Sets the visibility value for this WeatherReport.
     * 
     * @param visibility
     */
    public void setVisibility(com.capeclear.www.globalweather.xsd.Visibility visibility) {
        this.visibility = visibility;
    }


    /**
     * Gets the wind value for this WeatherReport.
     * 
     * @return wind
     */
    public com.capeclear.www.globalweather.xsd.Wind getWind() {
        return wind;
    }


    /**
     * Sets the wind value for this WeatherReport.
     * 
     * @param wind
     */
    public void setWind(com.capeclear.www.globalweather.xsd.Wind wind) {
        this.wind = wind;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WeatherReport)) return false;
        WeatherReport other = (WeatherReport) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.timestamp==null && other.getTimestamp()==null) || 
             (this.timestamp!=null &&
              this.timestamp.equals(other.getTimestamp()))) &&
            ((this.station==null && other.getStation()==null) || 
             (this.station!=null &&
              this.station.equals(other.getStation()))) &&
            ((this.phenomena==null && other.getPhenomena()==null) || 
             (this.phenomena!=null &&
              java.util.Arrays.equals(this.phenomena, other.getPhenomena()))) &&
            ((this.precipitation==null && other.getPrecipitation()==null) || 
             (this.precipitation!=null &&
              java.util.Arrays.equals(this.precipitation, other.getPrecipitation()))) &&
            ((this.extremes==null && other.getExtremes()==null) || 
             (this.extremes!=null &&
              java.util.Arrays.equals(this.extremes, other.getExtremes()))) &&
            ((this.pressure==null && other.getPressure()==null) || 
             (this.pressure!=null &&
              this.pressure.equals(other.getPressure()))) &&
            ((this.sky==null && other.getSky()==null) || 
             (this.sky!=null &&
              this.sky.equals(other.getSky()))) &&
            ((this.temperature==null && other.getTemperature()==null) || 
             (this.temperature!=null &&
              this.temperature.equals(other.getTemperature()))) &&
            ((this.visibility==null && other.getVisibility()==null) || 
             (this.visibility!=null &&
              this.visibility.equals(other.getVisibility()))) &&
            ((this.wind==null && other.getWind()==null) || 
             (this.wind!=null &&
              this.wind.equals(other.getWind())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getTimestamp() != null) {
            _hashCode += getTimestamp().hashCode();
        }
        if (getStation() != null) {
            _hashCode += getStation().hashCode();
        }
        if (getPhenomena() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPhenomena());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPhenomena(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPrecipitation() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPrecipitation());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPrecipitation(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getExtremes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getExtremes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getExtremes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPressure() != null) {
            _hashCode += getPressure().hashCode();
        }
        if (getSky() != null) {
            _hashCode += getSky().hashCode();
        }
        if (getTemperature() != null) {
            _hashCode += getTemperature().hashCode();
        }
        if (getVisibility() != null) {
            _hashCode += getVisibility().hashCode();
        }
        if (getWind() != null) {
            _hashCode += getWind().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WeatherReport.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "WeatherReport"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timestamp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "timestamp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("station");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "station"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "Station"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phenomena");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "phenomena"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "Phenomenon"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "phenomenon"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("precipitation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "precipitation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "Precipitation"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "precipitation"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extremes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "extremes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "Extreme"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "extreme"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pressure");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "pressure"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "Pressure"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sky");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "sky"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "Sky"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("temperature");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "temperature"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "Temperature"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("visibility");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "visibility"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "Visibility"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wind");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "wind"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "Wind"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
