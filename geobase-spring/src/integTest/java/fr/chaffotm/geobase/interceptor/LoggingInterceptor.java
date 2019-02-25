package fr.chaffotm.geobase.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class LoggingInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public ClientHttpResponse intercept(final HttpRequest request, final byte[] body,
                                        final ClientHttpRequestExecution execution) throws IOException {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("{} {} #PAYLOAD# {} |HEADERS| {}", request.getMethod(), request.getURI(),
                    new String(body), request.getHeaders());
        }
        return execution.execute(request, body);
    }

}
