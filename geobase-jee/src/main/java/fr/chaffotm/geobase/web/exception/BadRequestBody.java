package fr.chaffotm.geobase.web.exception;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BadRequestBody {

    private Set<String> messages;

    public BadRequestBody() {
        this.messages = new HashSet<>();
    }

    public Set<String> getMessages() {
        return messages;
    }

    public void setMessages(Set<String> messages) {
        this.messages = messages;
    }

    public void addMessage(String message) {
        this.messages.add(message);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BadRequestBody that = (BadRequestBody) o;
        return Objects.equals(messages, that.messages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messages);
    }

    @Override
    public String toString() {
        return "BadRequestBody{" +
                "messages=" + messages +
                '}';
    }

}
