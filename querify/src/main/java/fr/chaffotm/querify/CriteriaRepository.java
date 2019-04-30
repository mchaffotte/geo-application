package fr.chaffotm.querify;

import fr.chaffotm.querify.criteria.QueryCriteria;

import java.util.List;

public interface CriteriaRepository {

    <T> List<T> findAll(final int offset, final Integer limit, final QueryCriteria<T> criteria);

}
