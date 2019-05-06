package fr.chaffotm.geobase.repository;

import fr.chaffotm.querify.CriteriaRepository;
import fr.chaffotm.querify.JPACriteriaRepository;
import fr.chaffotm.querify.criteria.QueryCriteria;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class QueryCriteriaRepository implements CriteriaRepository {

    @PersistenceContext
    private EntityManager em;

    private CriteriaRepository queryCriteriaRepository;

    @PostConstruct
    public void initMaker() {
        queryCriteriaRepository = new JPACriteriaRepository(em);
    }

    @Override
    public <T> List<T> findAll(final int offset, final Integer limit, final QueryCriteria<T> criteria) {
        return queryCriteriaRepository.findAll(offset, limit, criteria);
    }

}
