package org.localstorm.camel.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * @author Alexey Kuznetsov
 */
@Path("/stocks")
public class StockPricesXmlResource {


        @POST
        //TODO: By the way, this 'Consumes' could be useful here.
        //@Consumes("application/xml")
        @Produces("text/plain")
        public String handle(byte[] s) {

            System.out.println("Yeah! XML: "+new String(s));
            //Return some cliched textual content
            return new String(s);
        }

  
}