package fr.chaffotm.geobase.repository;

import fr.chaffotm.geobase.domain.Country;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@ApplicationScoped
public class CountryRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Country> findAll() {
        final TypedQuery<Country> query = em.createNamedQuery("Country.findAll", Country.class);
        return  query.getResultList();
    }

}
