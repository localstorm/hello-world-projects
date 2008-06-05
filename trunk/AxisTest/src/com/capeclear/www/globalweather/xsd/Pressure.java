/**
 * Pressure.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.capeclear.www.globalweather.xsd;

public class Pressure  implements java.io.Serializable {
    private double altimeter;

    private double slp;

    private double delta;

    private int delta_hours;

    private java.lang.String string;

    public Pressure() {
    }

    public Pressure(
           double altimeter,
           double slp,
           double delta,
           int delta_hours,
           java.lang.String string) {
           this.altimeter = altimeter;
           this.slp = slp;
           this.delta = delta;
           this.delta_hours = delta_hours;
           this.string = string;
    }


    /**
     * Gets the altimeter value for this Pressure.
     * 
     * @return altimeter
     */
    public double getAltimeter() {
        return altimeter;
    }


    /**
     * Sets the altimeter value for this Pressure.
     * 
     * @param altimeter
     */
    public void setAltimeter(double altimeter) {
        this.altimeter = altimeter;
    }


    /**
     * Gets the slp value for this Pressure.
     * 
     * @return slp
     */
    public double getSlp() {
        return slp;
    }


    /**
     * Sets the slp value for this Pressure.
     * 
     * @param slp
     */
    public void setSlp(double slp) {
        this.slp = slp;
    }


    /**
     * Gets the delta value for this Pressure.
     * 
     * @return delta
     */
    public double getDelta() {
        return delta;
    }


    /**
     * Sets the delta value for this Pressure.
     * 
     * @param delta
     */
    public void setDelta(double delta) {
        this.delta = delta;
    }


    /**
     * Gets the delta_hours value for this Pressure.
     * 
     * @return delta_hours
     */
    public int getDelta_hours() {
        return delta_hours;
    }


    /**
     * Sets the delta_hours value for this Pressure.
     * 
     * @param delta_hours
     */
    public void setDelta_hours(int delta_hours) {
        this.delta_hours = delta_hours;
    }


    /**
     * Gets the string value for this Pressure.
     * 
     * @return string
     */
    public java.lang.String getString() {
        return string;
    }


    /**
     * Sets the string value for this Pressure.
     * 
     * @param string
     */
    public void setString(java.lang.String string) {
        this.string = string;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Pressure)) return false;
        Pressure other = (Pressure) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.altimeter == other.getAltimeter() &&
            this.slp == other.getSlp() &&
            this.delta == other.getDelta() &&
            this.delta_hours == other.getDelta_hours() &&
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
        _hashCode += new Double(getAltimeter()).hashCode();
        _hashCode += new Double(getSlp()).hashCode();
        _hashCode += new Double(getDelta()).hashCode();
        _hashCode += getDelta_hours();
        if (getString() != null) {
            _hashCode += getString().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Pressure.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "Pressure"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("altimeter");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "altimeter"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("slp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "slp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("delta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "delta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("delta_hours");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.capeclear.com/globalweather/xsd/", "delta_hours"));
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
