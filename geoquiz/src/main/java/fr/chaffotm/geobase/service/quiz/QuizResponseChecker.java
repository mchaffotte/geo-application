package fr.chaffotm.geobase.service.quiz;

import fr.chaffotm.geobase.domain.QuestionEntity;
import fr.chaffotm.geobase.domain.QuizEntity;
import fr.chaffotm.geobase.web.domain.QuizResponse;
import fr.chaffotm.geobase.web.domain.QuizResult;

import java.text.Collator;
import java.util.List;

public class QuizResponseChecker {

    private final Collator insensitiveStringComparator;

    public QuizResponseChecker() {
        insensitiveStringComparator = Collator.getInstance();
        insensitiveStringComparator.setStrength(Collator.PRIMARY);
    }

    public QuizResult score(final QuizEntity quizEntity, final QuizResponse quizResponse) {
        final List<QuestionEntity> questions = quizEntity.getQuestions();
        final List<String> answers = quizResponse.getAnswers();
        int nbOfCorrectAnswers = 0;
        for (int i = 0; i < questions.size(); i++) {
            final QuestionEntity question = questions.get(i);
            final String answer = answers.get(i);
            if (isSame(question.getAnswer(), answer)) {
                nbOfCorrectAnswers++;
            }
        }
        final QuizResult result = new QuizResult();
        result.setNbOfCorrectAnswers(nbOfCorrectAnswers);
        result.setNbOfQuestions(questions.size());
        return result;
    }

    private boolean isSame(final String answer1, final String answer2) {
        return insensitiveStringComparator.compare(answer1, answer2) == 0;
    }

}
