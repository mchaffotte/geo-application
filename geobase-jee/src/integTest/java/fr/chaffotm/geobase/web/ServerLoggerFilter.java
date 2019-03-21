package fr.chaffotm.geobase.web;

import fr.chaffotm.geobase.io.IOManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Provider
public class ServerLoggerFilter implements ContainerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerLoggerFilter.class);

    private final IOManager ioManager;

    public ServerLoggerFilter() {
        ioManager = new IOManager(StandardCharsets.UTF_8);
    }

    @Override
    public void filter(final ContainerRequestContext requestContext) throws IOException {
        if (LOGGER.isInfoEnabled()) {
            final String body;
            if (!requestContext.hasEntity()) {
                body = "";
            } else {
                body = ioManager.getContent(requestContext.getEntityStream());
                requestContext.setEntityStream(ioManager.newStream(body));
            }
            LOGGER.info("Client request: {} {} #PAYLOAD# {} |HEADERS| {}", requestContext.getMethod(),
                    requestContext.getUriInfo().getAbsolutePath(), body, requestContext.getHeaders());
        }
    }

}
