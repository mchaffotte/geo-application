package fr.chaffotm.geobase.web.rest;

import fr.chaffotm.geobase.service.ImageService;

import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.UUID;

@Path("images")
public class ImageEndpoint {

    private final ImageService imageService;

    public ImageEndpoint(final ImageService imageService) {
        this.imageService = imageService;
    }

    @GET
    @Path("{imageUuid}")
    @Produces("image/png")
    public Response get(@PathParam("imageUuid") final UUID imageUuid) {
        final InputStream stream = imageService.getImageStream(imageUuid);
        if (stream == null) {
            throw new NotFoundException("The uuid does not refer to any existing image: '" + imageUuid + "'");
        }
        final Response.ResponseBuilder responseBuilder = Response.ok(stream);
        responseBuilder.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imageUuid +"\"");
        return responseBuilder.build();
    }

}
