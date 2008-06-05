/**
 * Temperature.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.capeclear.www.globalweather.xsd;

public class Temperature  implements java.io.Serializable {
    private double ambient;

    private double dewpoint;

    private int relative_humidity;

    private java.lang.String string;

    public Temperature() {
    }

    public Temperature(
           double ambient,
           double dewpoint,
           int relative_humidity,
           java.lang.String string) {
           this.ambient = ambient;
           this.dewpoint = dewpoint;
           this.relative_humidity = relative_humidity;
           this.string = string;
    }


    /**
     * Gets the ambient value for this Temperature.
     * 
     * @return ambient
     */
    public double getAmbient() {
        return ambient;
    }


    /**
     * Sets the ambient value for this Temperature.
     * 
     * @param ambient
     */
    public void setAmbient(double ambient) {
        this.ambient = ambient;
    }


    /**
     * Gets the dewpoint value for this Temperature.
     * 
     * @return dewpoint
     */
    public double getDewpoint() {
        return dewpoint;
    }


    /**
     * Sets the dewpoint value for this Temperature.
     * 
     * @param dewpoint
     */
    public void setDewpoint(double dewpoint) {
        this.dewpoint = dewpoint;
    }


    /**
     * Gets the relative_humidity value for this Temperature.
     * 
     * @return relative_humidity
     */
    public int getRelative_humidity() {
        return relative_humidity;
    }


    /**
     * Sets the relative_humidity value for this Temperature.
     * 
     * @param relative_humidity
     */
    public void setRelative_humidity(int relative_humidity) {
        this.relative_humidity = relative_humidity;
    }


    /**
     * Gets the string value for this Temperature.
     * 
     * @return string
     */
    public java.lang.String getString() {
        return string;
    }


    /**
     * Sets the string value for this Temperature.
     * 
     * @param string
     */
    public void setString(java.lang.String string) {
        this.string = string;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Temperature)) return false;
        Temperature other = (Temperature) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.ambient == other.getAmbient() &&
            this.dewpoint == other.getDewpoint() &&
            this.relative_humidity == other.getRelative_humidity() &&
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
        _hashCode += new Double(getAmbient()).hashCode();
        _hashCode += new Double(getDewpoint()).hashCode();
        _hashCode += getRelative_humidity();
        if (getString() != null) {
            _hashCode += getString().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Temperature.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "Temperature"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ambient");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "ambient"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dewpoint");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "dewpoint"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("relative_humidity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "relative_humidity"));
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
