package fr.chaffotm.geobase.assertion;

import org.assertj.core.api.AbstractAssert;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

public class ResponseEntityAssert extends AbstractAssert<ResponseEntityAssert, ResponseEntity> {

    private ResponseEntityAssert(final ResponseEntity response) {
        super(response, ResponseEntityAssert.class);
    }

    public static ResponseEntityAssert assertThat(final ResponseEntity response) {
        return new ResponseEntityAssert(response);
    }

    public ResponseEntityAssert hasStatus(final HttpStatus status) {
        isNotNull();
        if (!Objects.equals(actual.getStatusCode(), status)) {
            failWithMessage("Expected response's status to be <%s> but was <%s>", status, actual.getStatusCode());
        }
        return this;
    }

    public ResponseEntityAssert hasNoBody() {
        return hasBody(null);
    }

    public ResponseEntityAssert hasBody(final Object bodyContent) {
        isNotNull();
        final Object body = actual.getBody();
        if (!Objects.equals(body, bodyContent)) {
            failWithMessage("Expected response's body to be <%s> but was <%s>", bodyContent, body);
        }
        return this;
    }

    public ResponseEntityAssert hasContentType(MediaType mediaType) {
        isNotNull();
        final MediaType contentType = actual.getHeaders().getContentType();
        if (!Objects.equals(contentType, mediaType)) {
            failWithMessage("Expected response's content type to be <%s> but was <%s>", mediaType, contentType);
        }
        return this;
    }

}
