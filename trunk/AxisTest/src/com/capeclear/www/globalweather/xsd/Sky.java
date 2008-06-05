/**
 * Sky.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.capeclear.www.globalweather.xsd;

public class Sky  implements java.io.Serializable {
    private double ceiling_altitude;

    private com.capeclear.www.globalweather.xsd.Layer[] layers;

    private java.lang.String string;

    public Sky() {
    }

    public Sky(
           double ceiling_altitude,
           com.capeclear.www.globalweather.xsd.Layer[] layers,
           java.lang.String string) {
           this.ceiling_altitude = ceiling_altitude;
           this.layers = layers;
           this.string = string;
    }


    /**
     * Gets the ceiling_altitude value for this Sky.
     * 
     * @return ceiling_altitude
     */
    public double getCeiling_altitude() {
        return ceiling_altitude;
    }


    /**
     * Sets the ceiling_altitude value for this Sky.
     * 
     * @param ceiling_altitude
     */
    public void setCeiling_altitude(double ceiling_altitude) {
        this.ceiling_altitude = ceiling_altitude;
    }


    /**
     * Gets the layers value for this Sky.
     * 
     * @return layers
     */
    public com.capeclear.www.globalweather.xsd.Layer[] getLayers() {
        return layers;
    }


    /**
     * Sets the layers value for this Sky.
     * 
     * @param layers
     */
    public void setLayers(com.capeclear.www.globalweather.xsd.Layer[] layers) {
        this.layers = layers;
    }


    /**
     * Gets the string value for this Sky.
     * 
     * @return string
     */
    public java.lang.String getString() {
        return string;
    }


    /**
     * Sets the string value for this Sky.
     * 
     * @param string
     */
    public void setString(java.lang.String string) {
        this.string = string;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Sky)) return false;
        Sky other = (Sky) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.ceiling_altitude == other.getCeiling_altitude() &&
            ((this.layers==null && other.getLayers()==null) || 
             (this.layers!=null &&
              java.util.Arrays.equals(this.layers, other.getLayers()))) &&
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
        _hashCode += new Double(getCeiling_altitude()).hashCode();
        if (getLayers() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getLayers());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getLayers(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getString() != null) {
            _hashCode += getString().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Sky.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "Sky"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ceiling_altitude");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "ceiling_altitude"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("layers");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "layers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "Layer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "layer"));
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
