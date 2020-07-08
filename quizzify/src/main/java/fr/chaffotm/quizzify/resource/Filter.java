package fr.chaffotm.quizzify.resource;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class Filter {

    @NotBlank
    private String name;

    @Min(0)
    private int value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Filter filter = (Filter) o;
        return value == filter.value &&
                Objects.equals(name, filter.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }
}
