/**
 * Layer.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.capeclear.www.globalweather.xsd;

public class Layer  implements java.io.Serializable {
    private double altitude;

    private int extent;

    private com.capeclear.www.globalweather.xsd.PhenomenonType type;

    private java.lang.String string;

    public Layer() {
    }

    public Layer(
           double altitude,
           int extent,
           com.capeclear.www.globalweather.xsd.PhenomenonType type,
           java.lang.String string) {
           this.altitude = altitude;
           this.extent = extent;
           this.type = type;
           this.string = string;
    }


    /**
     * Gets the altitude value for this Layer.
     * 
     * @return altitude
     */
    public double getAltitude() {
        return altitude;
    }


    /**
     * Sets the altitude value for this Layer.
     * 
     * @param altitude
     */
    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }


    /**
     * Gets the extent value for this Layer.
     * 
     * @return extent
     */
    public int getExtent() {
        return extent;
    }


    /**
     * Sets the extent value for this Layer.
     * 
     * @param extent
     */
    public void setExtent(int extent) {
        this.extent = extent;
    }


    /**
     * Gets the type value for this Layer.
     * 
     * @return type
     */
    public com.capeclear.www.globalweather.xsd.PhenomenonType getType() {
        return type;
    }


    /**
     * Sets the type value for this Layer.
     * 
     * @param type
     */
    public void setType(com.capeclear.www.globalweather.xsd.PhenomenonType type) {
        this.type = type;
    }


    /**
     * Gets the string value for this Layer.
     * 
     * @return string
     */
    public java.lang.String getString() {
        return string;
    }


    /**
     * Sets the string value for this Layer.
     * 
     * @param string
     */
    public void setString(java.lang.String string) {
        this.string = string;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Layer)) return false;
        Layer other = (Layer) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.altitude == other.getAltitude() &&
            this.extent == other.getExtent() &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
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
        _hashCode += new Double(getAltitude()).hashCode();
        _hashCode += getExtent();
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        if (getString() != null) {
            _hashCode += getString().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Layer.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "Layer"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("altitude");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "altitude"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extent");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "extent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "PhenomenonType"));
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
