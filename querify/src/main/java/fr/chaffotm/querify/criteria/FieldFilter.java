package fr.chaffotm.querify.criteria;

public interface FieldFilter {

    String getFieldName();

    void accept(FieldFilterVisitor visitor);

}
