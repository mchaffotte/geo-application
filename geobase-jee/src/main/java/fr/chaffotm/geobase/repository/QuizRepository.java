package fr.chaffotm.geobase.repository;

import fr.chaffotm.geobase.domain.QuizEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class QuizRepository {

    @PersistenceContext
    private EntityManager em;

    public QuizEntity create(final QuizEntity quiz) {
        if (quiz.getId() != null) {
            throw new IllegalArgumentException("id is already set");
        }
        em.persist(quiz);
        return quiz;
    }

    public QuizEntity get(final long id) {
        return em.find(QuizEntity.class, id);
    }

}
