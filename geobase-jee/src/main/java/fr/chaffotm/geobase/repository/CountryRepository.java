package fr.chaffotm.geobase.repository;

import fr.chaffotm.geobase.domain.CountryEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@ApplicationScoped
public class CountryRepository {

    @PersistenceContext
    private EntityManager em;

    public List<CountryEntity> findAll(final int offset, final Integer limit) {
        final TypedQuery<CountryEntity> query = em.createNamedQuery("Country.findAll", CountryEntity.class);
        query.setFirstResult(offset - 1);
        if (limit != null) {
            query.setMaxResults(limit);
        }
        return query.getResultList();
    }
 
    public long count() {
        final TypedQuery<Long> query = em.createNamedQuery("Country.count", Long.class);
        return query.getSingleResult();
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
        countryToUpdate.setPopulation(country.getPopulation());
        countryToUpdate.setTotalArea(country.getTotalArea());
        return em.merge(countryToUpdate);
    }

    public void delete(final long id) {
        final CountryEntity country = em.getReference(CountryEntity.class, id);
        em.remove(country);
    }

}
