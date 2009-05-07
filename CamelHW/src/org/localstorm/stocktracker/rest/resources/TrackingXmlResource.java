package org.localstorm.stocktracker.rest.resources;

import org.localstorm.stocktracker.util.io.TooLongStreamException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultExchange;
import org.localstorm.stocktracker.camel.CamelService;
import org.localstorm.stocktracker.camel.Endpoints;
import org.localstorm.stocktracker.camel.util.ProducerUtil;
import org.localstorm.stocktracker.exchange.StockChangeType;
import org.localstorm.stocktracker.exchange.StockEvent;
import org.localstorm.stocktracker.exchange.StockTrackingRequest;
import org.localstorm.stocktracker.camel.util.ExchangeFactory;
import org.localstorm.stocktracker.util.io.LimitedInputStream;

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

        // That is quite efficient event to create endpoint for each request
        this.ep = cc.getEndpoint(Endpoints.TRACKING_XML_INPUT_URI);
        this.channel = ep.createProducer();
    }


    @POST
    //TODO: By the way, this 'Consumes' could be useful here.
    //@Consumes("application/xml")
    @Produces("text/plain")
    public Response handle(InputStream is) {

        try {
            this.channel.start();

            // 10240 -- to configuration file
            LimitedInputStream lis = new LimitedInputStream(is, 10240L);

            // TODO: sending this to parsers

            StockTrackingRequest str = new StockTrackingRequest("localstorm");
            Calendar c = Calendar.getInstance();

            c.add(Calendar.SECOND, 10);
            Date end = c.getTime();

            str.addEvent(new StockEvent(StockChangeType.RAISE, "MSFT", new BigDecimal("10.1"), null, end));

            DefaultExchange ex = ExchangeFactory.inOut(ep, str);
            this.channel.process(ex);

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