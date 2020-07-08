package fr.chaffotm.querify.jpa;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class QueryExecutor {

    private final EntityManager em;

    public QueryExecutor(final EntityManager em) {
        this.em = em;
    }

    public List<?> execute(final String namedQuery, final List<Object> parameters) {
        final Query nativeQuery = em.createNamedQuery(namedQuery);
        int position = 1;
        for (Object parameter : parameters) {
            nativeQuery.setParameter(position, parameter);
            position++;
        }
        return nativeQuery.getResultList();
    }

}
