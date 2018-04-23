package fr.chaffotm.geobase.repository;

import java.util.ArrayList;
import java.util.List;

public class QueryCriteria {

    private Join join;

    private List<Sort> sorts;

    public QueryCriteria() {
        this.sorts = new ArrayList<>();
    }

    public Join getJoin() {
        return join;
    }

    public void setJoin(Join join) {
        this.join = join;
    }

    public List<Sort> getSorts() {
        return sorts;
    }

    public void setSorts(List<Sort> sorts) {
        this.sorts.clear();
        this.sorts.addAll(sorts);
    }

    public void addSort(Sort sort) {
        this.sorts.add(sort);
    }

}
