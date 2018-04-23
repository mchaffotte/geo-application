package fr.chaffotm.geobase.repository;

import fr.chaffotm.geobase.domain.AreaEntity;
import fr.chaffotm.geobase.domain.CountryEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CountryRepository {

    @PersistenceContext
    private EntityManager em;

    public List<CountryEntity> findAll(final int offset, final Integer limit, final QueryCriteria criteria) {
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final CriteriaQuery<CountryEntity> query = builder.createQuery(CountryEntity.class);
        final Root<CountryEntity> entityRoot = query.from(CountryEntity.class);
        entityRoot.alias("c");
        query.select(entityRoot);

        final Join<CountryEntity, AreaEntity> join;
        if (criteria.getJoin() != null) {
            join = entityRoot.join(criteria.getJoin().getEntityName());
        } else {
            join = null;
        }

        final List<Order> orders = new ArrayList<>();
        for (Sort sort : criteria.getSorts()) {
            final Path<Object> path;
            if (join != null) {
                path = join.get(sort.getPropertyName());
            } else {
                path = entityRoot.get(sort.getPropertyName());
            }
            final Order order;
            if (fr.chaffotm.geobase.repository.Order.ASC.equals(sort.getOrder())) {
                order = builder.asc(path);
            } else {
                order = builder.desc(path);
            }
            orders.add(order);
        }
        query.orderBy(orders);

        final TypedQuery<CountryEntity> typedQuery = em.createQuery(query);
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
