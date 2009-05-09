package org.localstorm.stocktracker.rest.resources;

import java.io.IOException;
import java.io.InputStream;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.xml.stream.XMLStreamException;
import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultExchange;
import org.localstorm.stocktracker.camel.CamelService;
import org.localstorm.stocktracker.camel.Endpoints;
import org.localstorm.stocktracker.camel.util.ProducerUtil;
import org.localstorm.stocktracker.exchange.StockTrackingRequest;
import org.localstorm.stocktracker.camel.util.ExchangeFactory;
import org.localstorm.stocktracker.rest.parsers.ObjectXmlReader;
import org.localstorm.stocktracker.rest.parsers.TrackingRequestParser;

/**
 * @author Alexey Kuznetsov
 */
@Path("/tracking")
public class TrackingXmlResource {

    private Endpoint ep;
    private Producer channel;

    @SuppressWarnings("unchecked") // don't want to write everywhere Producer<DefaultExchange>
    public TrackingXmlResource() throws Exception {
        CamelContext cc = CamelService.getInstance().getCamelContext();

        // That is quite efficient even to create endpoint for each request
        this.ep = cc.getEndpoint(Endpoints.TRACKING_REQUESTS_INPUT_URI);
        this.channel = ep.createProducer();
    }


    @POST
    //TODO: By the way, this 'Consumes' could be useful here.
    //@Consumes("application/xml")
    @Produces("text/plain")
    public Response handle(InputStream is) {

        ObjectXmlReader<StockTrackingRequest> reader = null;

        try {

            this.channel.start();

            // 10240 -- to configuration file
            reader = new ObjectXmlReader<StockTrackingRequest>(is, 10240L);

            // 1000 -- to config file
            StockTrackingRequest str = reader.getObject(new TrackingRequestParser(1000));

            DefaultExchange ex = ExchangeFactory.inOut(ep, str);
            this.channel.process(ex);

            return Response.ok(Constants.SUCCESS_RESPONSE).build();

        } catch(XMLStreamException e) {
            e.printStackTrace();
            //TODO: log!
            return Response.status(400).build(); // Bad request
        } catch(IOException e) {
            e.printStackTrace();
            //TODO: log!
            return Response.status(400).build(); // Bad request
        } catch(Exception e) {
            e.printStackTrace();
            return Response.status(500).build(); // Server error
        } finally {

            if ( reader!=null ) {
                reader.close();
            }

            ProducerUtil.stopQuietly(channel);
        }
    }
  
}