package org.localstorm.stocktracker.rest.resources;

import javax.ws.rs.core.Response;

/**
 * Just a bit eye candy in response building
 * @author Alexey Kuznetsov
 */
public class ResponseUtil 
{
    public static Response buildErrorResponse(Throwable e, int code) {
        return Response.status(code).entity(e.getMessage()).build();
    }

    public static Response buildOkResponse(Object o) {
        if (o!=null) {
            return Response.ok(o).build();
        } else {
            return Response.ok().build();
        }
    }
}
