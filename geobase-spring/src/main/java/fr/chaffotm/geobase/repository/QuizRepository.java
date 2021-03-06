package fr.chaffotm.geobase.repository;

import fr.chaffotm.quizzify.entity.ImageEntity;
import fr.chaffotm.quizzify.entity.QuizEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.UUID;

@Service
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
        final QuizEntity quiz = em.find(QuizEntity.class, id);
        if (quiz == null) {
            throw new EntityNotFoundException("Quiz not found with id " + id);
        }
        return quiz;
    }

    public ImageEntity getQuestionImage(final UUID imageUuid) {
        final TypedQuery<ImageEntity> query = em.createQuery("SELECT i FROM Image i WHERE i.uuid = :uuid", ImageEntity.class);
        query.setParameter("uuid", imageUuid);
        return query.getSingleResult();
    }

}
