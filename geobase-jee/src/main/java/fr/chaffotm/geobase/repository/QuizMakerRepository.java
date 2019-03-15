package fr.chaffotm.geobase.repository;

import fr.chaffotm.geoquiz.entity.QuizEntity;
import fr.chaffotm.geoquiz.service.QuizMaker;
import fr.chaffotm.geoquiz.resource.QuizConfiguration;
import fr.chaffotm.query.Repository;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class QuizMakerRepository {

    @PersistenceContext
    private EntityManager em;

    private QuizMaker quizMaker;

    @PostConstruct
    public void initMaker() {
        quizMaker = new QuizMaker(new Repository(em));
    }

    public QuizEntity build(QuizConfiguration configuration) {
        return quizMaker.build(configuration);
    }

}
