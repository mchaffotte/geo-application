package fr.chaffotm.geobase.repository;

import fr.chaffotm.geobase.domain.CountryEntity;
import fr.chaffotm.geobase.repository.criteria.QueryBuilder;
import fr.chaffotm.geobase.repository.criteria.Sort;
import fr.chaffotm.geobase.repository.criteria.SumFunction;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CountryRepository {

    @PersistenceContext
    private EntityManager em;

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

    public long count() {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Long> query = cb.createQuery(Long.class);
        final Root<CountryEntity> entity = query.from(CountryEntity.class);
        entity.alias("c");
        query.select(cb.count(entity));
        final TypedQuery<Long> typedQuery = em.createQuery(query);
        return typedQuery.getSingleResult();
    }

    public CountryEntity create(final CountryEntity country) {
        if (country.getId() != null) {
            throw new IllegalArgumentException("id is already set");
        }
        em.persist(country);
        return country;
    }

    public CountryEntity get(final long id) {
        final CountryEntity country = em.find(CountryEntity.class, id);
        if (country == null) {
            throw new EntityNotFoundException("Country not found with id" + id);
        }
        return country;
    }

    public CountryEntity update(final long id, final CountryEntity country) {
        if (country.getId() != null && country.getId() != id) {
                throw new IllegalArgumentException("ids are not the same");
        }
        final CountryEntity countryToUpdate = get(id);
        countryToUpdate.setName(country.getName());
        countryToUpdate.setArea(country.getArea());
        return em.merge(countryToUpdate);
    }

    public void delete(final long id) {
        final CountryEntity country = em.getReference(CountryEntity.class, id);
        em.remove(country);
    }

}
