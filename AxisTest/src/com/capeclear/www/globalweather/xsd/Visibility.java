/**
 * Visibility.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.capeclear.www.globalweather.xsd;

public class Visibility  implements java.io.Serializable {
    private double distance;

    private com.capeclear.www.globalweather.xsd.VisibilityQualifier qualifier;

    private java.lang.String string;

    public Visibility() {
    }

    public Visibility(
           double distance,
           com.capeclear.www.globalweather.xsd.VisibilityQualifier qualifier,
           java.lang.String string) {
           this.distance = distance;
           this.qualifier = qualifier;
           this.string = string;
    }


    /**
     * Gets the distance value for this Visibility.
     * 
     * @return distance
     */
    public double getDistance() {
        return distance;
    }


    /**
     * Sets the distance value for this Visibility.
     * 
     * @param distance
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }


    /**
     * Gets the qualifier value for this Visibility.
     * 
     * @return qualifier
     */
    public com.capeclear.www.globalweather.xsd.VisibilityQualifier getQualifier() {
        return qualifier;
    }


    /**
     * Sets the qualifier value for this Visibility.
     * 
     * @param qualifier
     */
    public void setQualifier(com.capeclear.www.globalweather.xsd.VisibilityQualifier qualifier) {
        this.qualifier = qualifier;
    }


    /**
     * Gets the string value for this Visibility.
     * 
     * @return string
     */
    public java.lang.String getString() {
        return string;
    }


    /**
     * Sets the string value for this Visibility.
     * 
     * @param string
     */
    public void setString(java.lang.String string) {
        this.string = string;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Visibility)) return false;
        Visibility other = (Visibility) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.distance == other.getDistance() &&
            ((this.qualifier==null && other.getQualifier()==null) || 
             (this.qualifier!=null &&
              this.qualifier.equals(other.getQualifier()))) &&
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
        _hashCode += new Double(getDistance()).hashCode();
        if (getQualifier() != null) {
            _hashCode += getQualifier().hashCode();
        }
        if (getString() != null) {
            _hashCode += getString().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Visibility.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "Visibility"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("distance");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "distance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("qualifier");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "qualifier"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "VisibilityQualifier"));
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
