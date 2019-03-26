package fr.chaffotm.geoquiz.service;

import fr.chaffotm.geoquiz.entity.AnswerEntity;
import fr.chaffotm.geoquiz.entity.QuestionEntity;
import fr.chaffotm.geoquiz.entity.QuizEntity;
import fr.chaffotm.geoquiz.resource.QuestionAnswer;
import fr.chaffotm.geoquiz.resource.QuizAnswer;
import fr.chaffotm.geoquiz.resource.QuizResult;

import java.text.Normalizer;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class QuizAnswerChecker {

    public QuizResult score(final QuizEntity quizEntity, final QuizAnswer quizAnswer) {
        final List<QuestionEntity> questions = quizEntity.getQuestions();
        final List<QuestionAnswer> answers = quizAnswer.getQuestionAnswers();
        int nbOfCorrectAnswers = 0;
        for (int i = 0; i < questions.size(); i++) {
            final QuestionEntity question = questions.get(i);
            final QuestionAnswer answer = answers.get(i);
            final Set<String> correctAnswers = getCorrectAnswers(question);
            final Set<String> normalizedAnswers = getNormalizedAnswers(answer);
            if (correctAnswers.equals(normalizedAnswers)) {
                nbOfCorrectAnswers++;
            }
        }
        final QuizResult result = new QuizResult();
        result.setNbOfCorrectAnswers(nbOfCorrectAnswers);
        result.setNbOfQuestions(questions.size());
        return result;
    }

    private Set<String> getNormalizedAnswers(final QuestionAnswer questionAnswer) {
        return questionAnswer.getAnswers().stream()
                .map(this::stripAccents)
                .collect(Collectors.toSet());
    }

    private Set<String> getCorrectAnswers(final QuestionEntity question) {
        return question.getAnswers().stream()
                .filter(AnswerEntity::isCorrect)
                .map(AnswerEntity::getAnswer)
                .map(this::stripAccents)
                .collect(Collectors.toSet());
    }

    private String stripAccents(final String s) {
        final String a = Normalizer.normalize(s.trim(), Normalizer.Form.NFD);
        return a.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "").toLowerCase();
    }

}
