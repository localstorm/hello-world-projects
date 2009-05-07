package org.localstorm.stocktracker.rest.resources;

import java.io.InputStream;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.Producer;
import org.localstorm.stocktracker.camel.CamelService;
import org.localstorm.stocktracker.camel.Endpoints;
import org.localstorm.stocktracker.camel.util.ProducerUtil;
import org.localstorm.stocktracker.util.io.LimitedInputStream;
import org.localstorm.stocktracker.util.io.TooLongStreamException;

/**
 * @author Alexey Kuznetsov
 */
@Path("/stocks")
public class StockPricesXmlResource {

    private Endpoint ep;
    private Producer channel;

    @SuppressWarnings("unchecked") // don't want to write everywhere Producer<DefaultExchange>
    public StockPricesXmlResource() throws Exception {
        CamelContext cc = CamelService.getInstance().getCamelContext();

        // That is quite efficient even to create endpoint for each request
        this.ep = cc.getEndpoint(Endpoints.STOCK_PRICES_INPUT_URI);
        this.channel = ep.createProducer();
    }


    @POST
    //TODO: By the way, this 'Consumes' could be useful here.
    //@Consumes("application/xml")
    @Produces("text/plain")
    public Response handle(InputStream is) {

        try {
            this.channel.start();

            // 30240 -- to configuration file
            LimitedInputStream lis = new LimitedInputStream(is, 30240L);

            // TODO: sending this to parsers

            return Response.ok().build();

            //TODO: some other exceptions? Parse exception->400?

        } catch(TooLongStreamException e ) {
            //TODO: log!
            return Response.status(400).build(); // Bad request
        } catch(Exception e) {
            return Response.status(500).build(); // Server error
        } finally {
            ProducerUtil.stopQuietly(channel);
        }
    }

  
}