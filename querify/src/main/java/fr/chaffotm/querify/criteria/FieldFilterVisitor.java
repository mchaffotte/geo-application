package fr.chaffotm.querify.criteria;

import fr.chaffotm.querify.criteria.filter.CollectionFieldFilter;
import fr.chaffotm.querify.criteria.filter.NoValueFieldFilter;

public interface FieldFilterVisitor {

    void visit(CollectionFieldFilter filter);

    void visit(NoValueFieldFilter noValueFieldFilter);
}
