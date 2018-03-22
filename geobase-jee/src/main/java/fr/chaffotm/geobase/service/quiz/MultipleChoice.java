package fr.chaffotm.geobase.service.quiz;

import fr.chaffotm.geobase.domain.CountryEntity;

import java.util.HashSet;
import java.util.Set;

public class MultipleChoice {

    private CountryEntity answer;

    private Set<CountryEntity> distractors;

    public MultipleChoice(final CountryEntity answer) {
        this.answer = answer;
        this.distractors = new HashSet<>();
    }

    public CountryEntity getAnswer() {
        return answer;
    }

    public Set<CountryEntity> getDistractors() {
        return distractors;
    }

    public void addDistractor(final CountryEntity distractor) {
        distractors.add(distractor);
    }

}
