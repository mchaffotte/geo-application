package fr.chaffotm.geobase.component;

import fr.chaffotm.geobase.io.IOManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ClientLoggerFilter implements ClientResponseFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientLoggerFilter.class.getName());

    private final IOManager ioManager;

    public ClientLoggerFilter() {
        ioManager = new IOManager(StandardCharsets.UTF_8);
    }

    @Override
    public void filter(final ClientRequestContext requestContext, final ClientResponseContext responseContext) throws IOException {
        if (LOGGER.isInfoEnabled()) {
            final String body;
            if (!responseContext.hasEntity()) {
                body = "";
            } else {
                body = ioManager.getContent(responseContext.getEntityStream());
                responseContext.setEntityStream(ioManager.newStream(body));
            }
            LOGGER.info("Server response: {} {} #PAYLOAD# {} |HEADERS| {}", responseContext.getStatus(),
                    responseContext.getStatusInfo(), body, responseContext.getHeaders());
        }
    }

}
