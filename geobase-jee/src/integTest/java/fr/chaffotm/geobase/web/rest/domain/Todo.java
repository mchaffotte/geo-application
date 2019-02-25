package fr.chaffotm.geobase.web.rest.domain;

import javax.validation.constraints.*;

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

}
