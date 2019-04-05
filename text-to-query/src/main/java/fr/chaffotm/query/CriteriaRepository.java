package fr.chaffotm.query;

import fr.chaffotm.query.criteria.QueryCriteria;

import java.util.List;

public interface CriteriaRepository {

    <T> List<T> findAll(final int offset, final Integer limit, final QueryCriteria<T> criteria);

}
