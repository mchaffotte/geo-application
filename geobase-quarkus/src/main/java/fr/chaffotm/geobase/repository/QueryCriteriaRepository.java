package fr.chaffotm.geobase.repository;

import fr.chaffotm.querify.CriteriaRepository;
import fr.chaffotm.querify.JPACriteriaRepository;
import fr.chaffotm.querify.criteria.QueryCriteria;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class QueryCriteriaRepository implements CriteriaRepository {

    @Inject
    EntityManager em;

    private CriteriaRepository queryCriteriaRepository;

    @PostConstruct
    public void initRepositoryMaker() {
        queryCriteriaRepository = new JPACriteriaRepository(em);
    }

    @Override
    public <T> List<T> findAll(final int offset, final Integer limit, final QueryCriteria<T> criteria) {
        return queryCriteriaRepository.findAll(offset, limit, criteria);
    }

}
