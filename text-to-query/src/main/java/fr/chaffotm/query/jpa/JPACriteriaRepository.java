package fr.chaffotm.query.jpa;

import fr.chaffotm.query.CriteriaRepository;
import fr.chaffotm.query.criteria.QueryCriteria;
import fr.chaffotm.query.criteria.Sort;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.stream.Collectors;

public class JPACriteriaRepository implements CriteriaRepository {

    private final EntityManager em;

    public JPACriteriaRepository(final EntityManager em) {
        this.em = em;
    }

    public <T> List<T> findAll(final int offset, final Integer limit, final QueryCriteria<T> criteria) {
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final Class<T> entityClass = criteria.getEntityClass();
        final CriteriaQueryBuilder<T> criteriaQueryBuilder = new CriteriaQueryBuilder<>(builder, criteria);
        final CriteriaQuery<FunctionEntity> query = criteriaQueryBuilder.build();
        final List<FunctionEntity> functionEntities = getResultList(query, offset, limit);
        return functionEntities.stream().map(entity -> entity.getEntity(entityClass)).collect(Collectors.toList());
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
