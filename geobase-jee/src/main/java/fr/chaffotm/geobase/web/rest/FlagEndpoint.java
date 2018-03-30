package fr.chaffotm.geobase.web.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.io.InputStream;

@Path("flags")
public class FlagEndpoint {

    @GET
    @Path("{id}")
    @Produces("image/png")
    public Response get(@PathParam("id") final String id) {
        final String fileName = id.toLowerCase() + ".png";
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final InputStream stream = classLoader.getResourceAsStream("/flags/" + fileName);
        final Response.ResponseBuilder responseBuilder = Response.ok(stream);
        responseBuilder.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName +"\"");
        return responseBuilder.build();
    }

}
