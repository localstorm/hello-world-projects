package org.localstorm.stocktracker.config;

import java.io.File;
import java.io.IOException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import org.localstorm.stocktracker.util.misc.ResourcesUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
public class GlobalConfiguration
{
    public static final String CONFIG_SCHEMA_RESOURCE_NAME = "META-INF/config.xsd";
    
    private static Configuration conf = null;

    public static void init(String pathToConfigFile) throws IOException {

        if (pathToConfigFile==null || pathToConfigFile.length()==0) {
            conf = new Configuration();
            // Default configuration here

            conf.setEventMinIntervalSize(5);           // 5 sec
            conf.setHost("localhost");
            conf.setPort(8080);
            conf.setPricesRequestMaxSize(3*1024*1024); // 3Mb
            conf.setRouteBuilderBeanShellScript(null); // Default routing
            conf.setTrackingRequestMaxSize(100*1024);  // 100kb
            conf.setUserMaxTrackingEventsQuota(100);
            conf.setPricesRequestMaxIssuers(20000);
            conf.setNotificationOutputFilePath("notifications.txt");
        } else {
            try {
                // Reading configuration using JAXB!
                
                JAXBContext ctx = JAXBContext.newInstance(Configuration.class);
                Unmarshaller um = ctx.createUnmarshaller();

                Schema   schema = ResourcesUtil.loadSchemaResource(CONFIG_SCHEMA_RESOURCE_NAME);
                um.setSchema(schema);
                conf = (Configuration) um.unmarshal(new File(pathToConfigFile));
            } catch(JAXBException e) {
                throw new IOException(e);
            }
        }
    }

    public static Configuration getConfiguration() {
        return conf;
    }

}
