package org.localstorm.restful;

import java.net.URI;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author Alexey Kuznetsov
 */
@Path("/myResource")
@Produces("text/plain")
public class SomeResource {

    @GET
    public String doGetAsPlainText() {
        return "Plain Text";
    }

    @GET
    @Produces("text/html")
    public String doGetAsHtml() {
        return "<b>HTML text</b>";
    }

    @Path("/smooth")
    @Produces("text/xml")
    @GET
    public Response doGetSmooth()
    {
        return Response.ok("<b>Smooth text</b>").build();
    }

    @Path("/redirect")
    @GET
    public Response doRedirect(@QueryParam("where") String where)
    {
        return Response.seeOther(URI.create("https://localhost:1443/"+where)).build();
    }
}
