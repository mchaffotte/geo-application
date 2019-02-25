package fr.chaffotm.geobase.assertion;

import fr.chaffotm.geobase.web.domain.Frame;
import fr.chaffotm.geobase.web.exception.BadRequestBody;
import fr.chaffotm.geobase.web.exception.ErrorBody;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ListAssert;
import org.assertj.core.api.ObjectAssert;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;
import java.util.Objects;

public class ResponseAssert extends AbstractAssert<ResponseAssert, Response> {

    private ResponseAssert(final Response response) {
        super(response, ResponseAssert.class);
    }

    public static ResponseAssert assertThat(final Response response) {
        return new ResponseAssert(response);
    }

    public ResponseAssert hasStatus(final Status status) {
        isNotNull();
        if (!Objects.equals(actual.getStatus(), status.getStatusCode())) {
            failWithMessage("Expected response's status to be <%s> but was <%s>",
                    status.getStatusCode(), actual.getStatus());
        }
        return this;
    }

    public ResponseAssert hasNoBody() {
        return hasBody(String.class, "");
    }

    public ResponseAssert hasBody(final ErrorBody errorBody) {
        return hasBody(ErrorBody.class, errorBody);
    }

    public ResponseAssert hasBody(final BadRequestBody errorBody) {
        return hasBody(BadRequestBody.class, errorBody);
    }

    private <T> T getBody(final Class<T> bodyClass) {
        isNotNull();
        return actual.readEntity(bodyClass);
    }

    private <T> ResponseAssert hasBody(final Class<T> bodyClass, final T bodyContent) {
        final T body = getBody(bodyClass);
        if (!Objects.equals(body, bodyContent)) {
            failWithMessage("Expected response's body to be <%s> but was <%s>", bodyContent, body);
        }
        return this;
    }

    public <T> ObjectAssert<T> withBody(final Class<T> bodyClass) {
        final T body = getBody(bodyClass);
        return Assertions.assertThat(body);
    }

    public <T> ListAssert<T> withBodyList(final Class<T> bodyClass) {
        isNotNull();
        final ListParameterizedType listType = new ListParameterizedType(bodyClass);
        final List<T> body = actual.readEntity(new GenericType<List<T>>(listType) {});
        return Assertions.assertThat(body);
    }

    public <T> ObjectAssert<Frame<T>> withBodyFrame(final Class<T> bodyClass) {
        isNotNull();
        final FrameParameterizedType listType = new FrameParameterizedType(bodyClass);
        final Frame<T> body = actual.readEntity(new GenericType<Frame<T>>(listType) {});
        return Assertions.assertThat(body);
    }

}
