package fr.chaffotm.querify.criteria.filter;

import fr.chaffotm.querify.criteria.FieldFilter;
import fr.chaffotm.querify.criteria.FieldFilterVisitor;

import java.util.List;

public class CollectionFieldFilter implements FieldFilter {

    private final String fieldName;

    private final CollectionOperator operator;

    private final List<String> elements;

    public CollectionFieldFilter(final String fieldName, final CollectionOperator operator, final String... elements) {
        this.fieldName = fieldName;
        this.operator = operator;
        this.elements = List.of(elements);
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    public CollectionOperator getOperator() {
        return operator;
    }

    public List<String> getElements() {
        return elements;
    }

    @Override
    public void accept(final FieldFilterVisitor visitor) {
        visitor.visit(this);
    }

}
