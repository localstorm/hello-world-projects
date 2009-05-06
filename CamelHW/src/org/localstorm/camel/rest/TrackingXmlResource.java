package org.localstorm.camel.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * @author Alexey Kuznetsov
 */
@Path("/tracking")
public class TrackingXmlResource {


        @POST
        //TODO: By the way, this 'Consumes' could be useful here.
        //@Consumes("application/xml")
        @Produces("text/plain")
        public String handle(InputStream s) throws IOException {

            
            System.out.println("Yeah! XML! ");

            BufferedReader br = new BufferedReader(new InputStreamReader(s));
            //Return some cliched textual content
            return s.getClass().getName();
        }

  
}