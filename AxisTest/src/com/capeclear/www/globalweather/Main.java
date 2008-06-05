package com.capeclear.www.globalweather;

import com.capeclear.www.globalweather.wsdl.GlobalWeather_ServiceLocator;
import com.capeclear.www.globalweather.wsdl.GlobalWeather_PortType;
import com.capeclear.www.globalweather.xsd.WeatherReport;

/**
 * Just used:
 * java -cp axis.jar;commons-logging-1.0.4.jar;wsdl4j-1.5.1.jar;saaj.jar;commons-discovery-0.2.jar;jaxrpc.jar org.apache.axis.wsdl.WSDL2Java http://live.capeclear.com/ccx/GlobalWeather?wsdl
 */
public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Simple Web-service usage with AXIS. Weather in Dublin airport");
        GlobalWeather_ServiceLocator sl = new GlobalWeather_ServiceLocator();
        GlobalWeather_PortType glow = sl.getGlobalWeather();

        WeatherReport wr = glow.getWeatherReport("DUB");

        System.out.println("Temperature: \t" + wr.getTemperature().getString());
        System.out.println("Station:\t\t" + wr.getStation().getWmo());
        System.out.println("Visibility:\t\t" + wr.getVisibility().getDistance());
    }

}
