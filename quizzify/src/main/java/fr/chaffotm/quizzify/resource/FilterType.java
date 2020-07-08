package fr.chaffotm.quizzify.resource;

import java.util.List;
import java.util.Objects;

public class FilterType {

    private String label;

    private String name;

    private List<Possibility> values;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Possibility> getValues() {
        return values;
    }

    public void setValues(List<Possibility> values) {
        this.values = values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilterType that = (FilterType) o;
        return Objects.equals(label, that.label) &&
                Objects.equals(name, that.name) &&
                Objects.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, name, values);
    }

    @Override
    public String toString() {
        return "FilterType{" +
                "label='" + label + '\'' +
                ", name='" + name + '\'' +
                ", values=" + values +
                '}';
    }
}
