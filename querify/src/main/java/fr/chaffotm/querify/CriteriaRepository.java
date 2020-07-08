package fr.chaffotm.querify;

import fr.chaffotm.querify.criteria.QueryCriteria;

import java.util.List;

public interface CriteriaRepository {

    <T> List<T> findAll(int offset, Integer limit, QueryCriteria<T> criteria);

    <T> List<T> findAll(String query, Class<T> resultClass);

}
