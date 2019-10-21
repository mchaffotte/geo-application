package fr.chaffotm.quizzify.service;

import java.util.HashSet;
import java.util.Set;

public class MultipleChoice<T> {

    private T answer;

    private Set<T> distractors;

    public MultipleChoice(final T answer) {
        this.answer = answer;
        this.distractors = new HashSet<>();
    }

    public T getAnswer() {
        return answer;
    }

    public Set<T> getDistractors() {
        return distractors;
    }

    public void addDistractor(final T distractor) {
        distractors.add(distractor);
    }

}
