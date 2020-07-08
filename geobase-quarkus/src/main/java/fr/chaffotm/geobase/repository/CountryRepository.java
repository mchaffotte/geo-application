package fr.chaffotm.geobase.repository;

import fr.chaffotm.geodata.entity.CountryEntity;
import fr.chaffotm.querify.CriteriaRepository;
import fr.chaffotm.querify.JPACriteriaRepository;
import fr.chaffotm.querify.criteria.QueryCriteria;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@ApplicationScoped
public class CountryRepository {

    @Inject
    EntityManager em;

    public List<CountryEntity> findAll(final int offset, final Integer limit, final QueryCriteria<CountryEntity> criteria) {
        final CriteriaRepository repository = new JPACriteriaRepository(em);
        return repository.findAll(offset, limit, criteria);
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
