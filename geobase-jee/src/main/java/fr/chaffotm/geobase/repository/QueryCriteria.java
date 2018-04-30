package fr.chaffotm.geobase.repository;

import fr.chaffotm.geobase.repository.criteria.JoinEntity;
import fr.chaffotm.geobase.repository.criteria.Order;
import fr.chaffotm.geobase.repository.criteria.Sort;

import java.util.ArrayList;
import java.util.List;

public class QueryCriteria {

    private JoinEntity join;

    private List<Sort> sorts;

    public QueryCriteria() {
        this.sorts = new ArrayList<>();
    }

    public JoinEntity getJoin() {
        return join;
    }

    public void setJoin(final String name) {
        this.join = new JoinEntity(name);
    }

    public List<Sort> getSorts() {
        return sorts;
    }

    public void addSort(final String sortAttribute, final Order order) {
        final Sort sort = new Sort(sortAttribute, order);
        this.sorts.add(sort);
    }

    public void setSorts(List<Sort> sorts) {
        this.sorts.clear();
        this.sorts.addAll(sorts);
    }

}
