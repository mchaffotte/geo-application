package fr.chaffotm.querify.criteria;

import fr.chaffotm.querify.criteria.filter.CollectionFieldFilter;
import fr.chaffotm.querify.criteria.filter.NoValueFieldFilter;
import fr.chaffotm.querify.criteria.filter.ValueFieldFilter;

public interface FieldFilterVisitor {

    void visit(CollectionFieldFilter filter);

    void visit(NoValueFieldFilter noValueFieldFilter);

    void visit(ValueFieldFilter valueFieldFilter);

}
