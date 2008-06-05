/**
 * Wind.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.capeclear.www.globalweather.xsd;

public class Wind  implements java.io.Serializable {
    private double prevailing_speed;

    private double gust_speed;

    private com.capeclear.www.globalweather.xsd.Direction prevailing_direction;

    private com.capeclear.www.globalweather.xsd.Direction varying_from_direction;

    private com.capeclear.www.globalweather.xsd.Direction varying_to_direction;

    private java.lang.String string;

    public Wind() {
    }

    public Wind(
           double prevailing_speed,
           double gust_speed,
           com.capeclear.www.globalweather.xsd.Direction prevailing_direction,
           com.capeclear.www.globalweather.xsd.Direction varying_from_direction,
           com.capeclear.www.globalweather.xsd.Direction varying_to_direction,
           java.lang.String string) {
           this.prevailing_speed = prevailing_speed;
           this.gust_speed = gust_speed;
           this.prevailing_direction = prevailing_direction;
           this.varying_from_direction = varying_from_direction;
           this.varying_to_direction = varying_to_direction;
           this.string = string;
    }


    /**
     * Gets the prevailing_speed value for this Wind.
     * 
     * @return prevailing_speed
     */
    public double getPrevailing_speed() {
        return prevailing_speed;
    }


    /**
     * Sets the prevailing_speed value for this Wind.
     * 
     * @param prevailing_speed
     */
    public void setPrevailing_speed(double prevailing_speed) {
        this.prevailing_speed = prevailing_speed;
    }


    /**
     * Gets the gust_speed value for this Wind.
     * 
     * @return gust_speed
     */
    public double getGust_speed() {
        return gust_speed;
    }


    /**
     * Sets the gust_speed value for this Wind.
     * 
     * @param gust_speed
     */
    public void setGust_speed(double gust_speed) {
        this.gust_speed = gust_speed;
    }


    /**
     * Gets the prevailing_direction value for this Wind.
     * 
     * @return prevailing_direction
     */
    public com.capeclear.www.globalweather.xsd.Direction getPrevailing_direction() {
        return prevailing_direction;
    }


    /**
     * Sets the prevailing_direction value for this Wind.
     * 
     * @param prevailing_direction
     */
    public void setPrevailing_direction(com.capeclear.www.globalweather.xsd.Direction prevailing_direction) {
        this.prevailing_direction = prevailing_direction;
    }


    /**
     * Gets the varying_from_direction value for this Wind.
     * 
     * @return varying_from_direction
     */
    public com.capeclear.www.globalweather.xsd.Direction getVarying_from_direction() {
        return varying_from_direction;
    }


    /**
     * Sets the varying_from_direction value for this Wind.
     * 
     * @param varying_from_direction
     */
    public void setVarying_from_direction(com.capeclear.www.globalweather.xsd.Direction varying_from_direction) {
        this.varying_from_direction = varying_from_direction;
    }


    /**
     * Gets the varying_to_direction value for this Wind.
     * 
     * @return varying_to_direction
     */
    public com.capeclear.www.globalweather.xsd.Direction getVarying_to_direction() {
        return varying_to_direction;
    }


    /**
     * Sets the varying_to_direction value for this Wind.
     * 
     * @param varying_to_direction
     */
    public void setVarying_to_direction(com.capeclear.www.globalweather.xsd.Direction varying_to_direction) {
        this.varying_to_direction = varying_to_direction;
    }


    /**
     * Gets the string value for this Wind.
     * 
     * @return string
     */
    public java.lang.String getString() {
        return string;
    }


    /**
     * Sets the string value for this Wind.
     * 
     * @param string
     */
    public void setString(java.lang.String string) {
        this.string = string;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Wind)) return false;
        Wind other = (Wind) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.prevailing_speed == other.getPrevailing_speed() &&
            this.gust_speed == other.getGust_speed() &&
            ((this.prevailing_direction==null && other.getPrevailing_direction()==null) || 
             (this.prevailing_direction!=null &&
              this.prevailing_direction.equals(other.getPrevailing_direction()))) &&
            ((this.varying_from_direction==null && other.getVarying_from_direction()==null) || 
             (this.varying_from_direction!=null &&
              this.varying_from_direction.equals(other.getVarying_from_direction()))) &&
            ((this.varying_to_direction==null && other.getVarying_to_direction()==null) || 
             (this.varying_to_direction!=null &&
              this.varying_to_direction.equals(other.getVarying_to_direction()))) &&
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
        _hashCode += new Double(getPrevailing_speed()).hashCode();
        _hashCode += new Double(getGust_speed()).hashCode();
        if (getPrevailing_direction() != null) {
            _hashCode += getPrevailing_direction().hashCode();
        }
        if (getVarying_from_direction() != null) {
            _hashCode += getVarying_from_direction().hashCode();
        }
        if (getVarying_to_direction() != null) {
            _hashCode += getVarying_to_direction().hashCode();
        }
        if (getString() != null) {
            _hashCode += getString().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Wind.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "Wind"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prevailing_speed");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "prevailing_speed"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gust_speed");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "gust_speed"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prevailing_direction");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "prevailing_direction"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "Direction"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("varying_from_direction");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "varying_from_direction"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "Direction"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("varying_to_direction");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "varying_to_direction"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "Direction"));
        elemField.setMinOccurs(0);
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
