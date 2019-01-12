package fr.chaffotm.geobase.repository;

import fr.chaffotm.geobase.repository.criteria.QueryBuilder;
import fr.chaffotm.geobase.repository.criteria.Sort;
import fr.chaffotm.geobase.repository.criteria.SumFunction;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.stream.Collectors;

public class Repository {

    private final EntityManager em;

    public Repository(final EntityManager em) {
        this.em = em;
    }

    public <T> List<T> findAll(final int offset, final Integer limit, final QueryCriteria criteria, final Class<T> entityClass) {
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final QueryBuilder<T, T> queryBuilder = new QueryBuilder<>(builder, entityClass, entityClass);
        if (criteria.getJoin() != null) {
            queryBuilder.addJoin(criteria.getJoin());
        }
        final List<Sort> sorts = criteria.getSorts();
        for (Sort sort : sorts) {
            queryBuilder.addSort(sort);
        }
        final CriteriaQuery<T> query = queryBuilder.build();
        return getResultList(query, offset, limit);
    }

    public <T> List<T> findAll(final int offset, final Integer limit, final FunctionCriteria criteria, final Class<T> entityClass) {
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final QueryBuilder<FunctionEntity, T> queryBuilder = new QueryBuilder<>(builder, FunctionEntity.class, entityClass);
        if (criteria.getJoin() != null) {
            queryBuilder.addJoin(criteria.getJoin());
        }
        final SumFunction function = criteria.getFunction();
        queryBuilder.setFunction(function, criteria.getJoin());
        final List<Sort> sorts = criteria.getSorts();
        for (Sort sort : sorts) {
            queryBuilder.addSort(sort);
        }
        final CriteriaQuery<FunctionEntity> query = queryBuilder.build();
        final List<FunctionEntity> arithmeticCountries = getResultList(query, offset, limit);
        return arithmeticCountries.stream().map(entity -> (T) entity.getEntity()).collect(Collectors.toList());
    }

    private <T> List<T> getResultList(final CriteriaQuery<T> query, final int offset, final Integer limit) {
        final TypedQuery<T> typedQuery = em.createQuery(query);
        typedQuery.setFirstResult(offset - 1);
        if (limit != null) {
            typedQuery.setMaxResults(limit);
        }
        return typedQuery.getResultList();
    }

}
