/**
 * Extreme.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.capeclear.www.globalweather.xsd;

public class Extreme  implements java.io.Serializable {
    private com.capeclear.www.globalweather.xsd.Temperature temperature;

    private com.capeclear.www.globalweather.xsd.ExtremeType type;

    private int hours;

    private java.lang.String string;

    public Extreme() {
    }

    public Extreme(
           com.capeclear.www.globalweather.xsd.Temperature temperature,
           com.capeclear.www.globalweather.xsd.ExtremeType type,
           int hours,
           java.lang.String string) {
           this.temperature = temperature;
           this.type = type;
           this.hours = hours;
           this.string = string;
    }


    /**
     * Gets the temperature value for this Extreme.
     * 
     * @return temperature
     */
    public com.capeclear.www.globalweather.xsd.Temperature getTemperature() {
        return temperature;
    }


    /**
     * Sets the temperature value for this Extreme.
     * 
     * @param temperature
     */
    public void setTemperature(com.capeclear.www.globalweather.xsd.Temperature temperature) {
        this.temperature = temperature;
    }


    /**
     * Gets the type value for this Extreme.
     * 
     * @return type
     */
    public com.capeclear.www.globalweather.xsd.ExtremeType getType() {
        return type;
    }


    /**
     * Sets the type value for this Extreme.
     * 
     * @param type
     */
    public void setType(com.capeclear.www.globalweather.xsd.ExtremeType type) {
        this.type = type;
    }


    /**
     * Gets the hours value for this Extreme.
     * 
     * @return hours
     */
    public int getHours() {
        return hours;
    }


    /**
     * Sets the hours value for this Extreme.
     * 
     * @param hours
     */
    public void setHours(int hours) {
        this.hours = hours;
    }


    /**
     * Gets the string value for this Extreme.
     * 
     * @return string
     */
    public java.lang.String getString() {
        return string;
    }


    /**
     * Sets the string value for this Extreme.
     * 
     * @param string
     */
    public void setString(java.lang.String string) {
        this.string = string;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Extreme)) return false;
        Extreme other = (Extreme) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.temperature==null && other.getTemperature()==null) || 
             (this.temperature!=null &&
              this.temperature.equals(other.getTemperature()))) &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
            this.hours == other.getHours() &&
            ((this.string==null && other.getString()==null) || 
             (this.string!=null &&
              this.string.equals(other.getString())));
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
        if (getTemperature() != null) {
            _hashCode += getTemperature().hashCode();
        }
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        _hashCode += getHours();
        if (getString() != null) {
            _hashCode += getString().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Extreme.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "Extreme"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("temperature");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "temperature"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "Temperature"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "ExtremeType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hours");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "hours"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("string");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "string"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
