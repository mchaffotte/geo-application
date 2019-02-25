package fr.chaffotm.geobase.configuration;

import java.util.Objects;

public class ErrorBody {

    private Long errorCode;

    private String message;

    public Long getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Long errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorBody errorBody = (ErrorBody) o;
        return Objects.equals(errorCode, errorBody.errorCode) &&
                Objects.equals(message, errorBody.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorCode, message);
    }

    @Override
    public String toString() {
        return "ErrorBody{" +
                "errorCode=" + errorCode +
                ", message='" + message + '\'' +
                '}';
    }

}
