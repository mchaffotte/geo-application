package fr.chaffotm.querify;

import fr.chaffotm.querify.criteria.QueryCriteria;
import fr.chaffotm.querify.criteria.Sort;
import fr.chaffotm.querify.jpa.CriteriaQueryBuilder;
import fr.chaffotm.querify.jpa.FunctionEntity;
import fr.chaffotm.querify.jpa.QueryExecutor;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.stream.Collectors;

public class JPACriteriaRepository implements CriteriaRepository {

    private final EntityManager em;

    private final QueryExecutor executor;

    public JPACriteriaRepository(final EntityManager em) {
        this.em = em;
        this.executor = new QueryExecutor(em);
    }

    public <T> List<T> findAll(final int offset, final Integer limit, final QueryCriteria<T> criteria) {
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final Class<T> entityClass = criteria.getEntityClass();
        final CriteriaQuery<FunctionEntity> query = buildQuery(builder, criteria);
        final List<FunctionEntity> functionEntities = getResultList(query, offset, limit);
        return functionEntities.stream().map(entity -> entity.getEntity(entityClass)).collect(Collectors.toList());
    }

    @Override
    public <T> List<T> findAll(final String query, final Class<T> resultClass) {
        final TypedQuery<T> namedQuery = em.createNamedQuery(query, resultClass);
        return namedQuery.getResultList();
    }

    private <T> CriteriaQuery<FunctionEntity> buildQuery(final CriteriaBuilder builder, final QueryCriteria<T> criteria) {
        final Class<T> entityClass = criteria.getEntityClass();
        final CriteriaQueryBuilder<T> criteriaQueryBuilder = new CriteriaQueryBuilder<>(builder, executor, entityClass);
        if (criteria.getFunction() != null) {
            criteriaQueryBuilder.function(criteria.getFunction());
        }
        if (criteria.getJoin() != null) {
            criteriaQueryBuilder.join(criteria.getJoin());
        }
        if (criteria.getFilter() != null) {
            criteriaQueryBuilder.filter(criteria.getFilter());
        }
        final List<Sort> sorts = criteria.getSorts();
        for (Sort sort : sorts) {
            criteriaQueryBuilder.sort(sort);
        }
        return criteriaQueryBuilder.build();
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
