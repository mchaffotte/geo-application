package fr.chaffotm.quizzify.resource;

import java.util.Objects;

public class Possibility {

    private String label;

    private long value;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Possibility that = (Possibility) o;
        return value == that.value &&
                Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, value);
    }

    @Override
    public String toString() {
        return "Possibility{" +
                "label='" + label + '\'' +
                ", value=" + value +
                '}';
    }
}

