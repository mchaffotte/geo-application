package fr.chaffotm.geobase.web.rest.domain;

import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement
public class Todo {

    @NotNull(message = "Title cannot be null")
    private String title;

    @NotBlank
    private String message;

    @Email
    private String email;

    @Positive
    @Max(value = 10)
    private int priority;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return priority == todo.priority &&
                Objects.equals(title, todo.title) &&
                Objects.equals(message, todo.message) &&
                Objects.equals(email, todo.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, message, email, priority);
    }

    @Override
    public String toString() {
        return "Todo{" +
                "title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", email='" + email + '\'' +
                ", priority=" + priority +
                '}';
    }

}
