package fr.chaffotm.geobase.repository;

import fr.chaffotm.geobase.domain.CountryEntity;
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

    public List<CountryEntity> findAll(final int offset, final Integer limit, final QueryCriteria criteria) {
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final QueryBuilder<CountryEntity, CountryEntity> queryBuilder = new QueryBuilder<>(builder, CountryEntity.class, CountryEntity.class);
        if (criteria.getJoin() != null) {
            queryBuilder.addJoin(criteria.getJoin());
        }
        final List<Sort> sorts = criteria.getSorts();
        for (Sort sort : sorts) {
            queryBuilder.addSort(sort);
        }
        final CriteriaQuery<CountryEntity> query = queryBuilder.build();
        return getResultList(query, offset, limit);
    }

    public List<CountryEntity> findAll(final int offset, final Integer limit, final FunctionCriteria criteria) {
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final QueryBuilder<FunctionCountryEntity, CountryEntity> queryBuilder = new QueryBuilder<>(builder, FunctionCountryEntity.class, CountryEntity.class);
        if (criteria.getJoin() != null) {
            queryBuilder.addJoin(criteria.getJoin());
        }
        final SumFunction function = criteria.getFunction();
        queryBuilder.setFunction(function, criteria.getJoin());
        final List<Sort> sorts = criteria.getSorts();
        for (Sort sort : sorts) {
            queryBuilder.addSort(sort);
        }
        final CriteriaQuery<FunctionCountryEntity> query = queryBuilder.build();
        final List<FunctionCountryEntity> arithmeticCountries = getResultList(query, offset, limit);
        return arithmeticCountries.stream().map(FunctionCountryEntity::getEntity).collect(Collectors.toList());
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
