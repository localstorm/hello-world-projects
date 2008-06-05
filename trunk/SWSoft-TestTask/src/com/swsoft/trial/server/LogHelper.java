package com.swsoft.trial.server;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.swsoft.trial.common.ThreadSafe;
import com.swsoft.trial.util.ResourceLoader;

import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

/**
 * This class provides server-level logger instance
 * @author Kuznetsov A.
 * @version 1.0
 */
@ThreadSafe
class LogHelper {

        private static Logger serverLogger = null;
        
        private static final String LOG_RESOURCE_ID         = "log4j.properties";
        private static final String SERVER_LOG_NAME         = "sys.server";
        private static final String SERVER_LOG_STARTED      = "Logging started.";

        static {
        	try {
        		
        		configure();
        		serverLogger = Logger.getLogger(SERVER_LOG_NAME);
        		serverLogger.info(SERVER_LOG_STARTED);
        		
            }catch(IOException e){
              	e.printStackTrace();
            }
        }

        /**
         * Returns server-level logger
         * @return server-level logger
         * @throws LoggerConfigurationException if logging subsystem is not configured
         */
        public static Logger getLogger() {
        	if (serverLogger==null) {
        		throw new RuntimeException("Logger not created.");
            }
            return serverLogger;
        }


    	private static void configure() throws IOException {
    		InputStream propStream = ResourceLoader.loadResource(LOG_RESOURCE_ID);
    		Properties prop = new Properties();

    		// Following call closes Stream!
    		prop.load(propStream);
    		PropertyConfigurator.configure(prop);
    	}

}
