package fr.chaffotm.quizzify.service.generator;

import fr.chaffotm.geodata.entity.CountryEntity;
import fr.chaffotm.quizzify.resource.AnswerType;
import fr.chaffotm.quizzify.service.MultipleChoice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public abstract class RandomGenerator implements Generator {

    private static final int NUMBER_OF_QUESTIONS = 10;

    private static final int NUMBER_OF_DISTRACTORS = 3;

    @Override
    public List<MultipleChoice> generate(final List<CountryEntity> countries, final AnswerType answerType) {
        final int size = countries.size();
        final Set<Integer> excludeIds = new HashSet<>(exclude(size));
        final List<MultipleChoice> multipleChoices = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_QUESTIONS; i++) {
            final int countryIndex = getRandomIndex(size, excludeIds);
            excludeIds.add(countryIndex);
            final CountryEntity answer = countries.get(countryIndex);
            final MultipleChoice multipleChoice = new MultipleChoice(answer);
            if (AnswerType.MULTIPLE_CHOICE == answerType) {
                final List<CountryEntity> distractors = findRandom(countries, countryIndex);
                for (CountryEntity distractor : distractors) {
                    multipleChoice.addDistractor(distractor);
                }
            }
            multipleChoices.add(multipleChoice);
        }
        return multipleChoices;
    }

    private int getRandomIndex(final int max, final Set<Integer> excludedIds) {
        return getRandomIndex(0, max, excludedIds);
    }

    private int getRandomIndex(final int min, final int max, final Set<Integer> excludedIds) {
        int index;
        do {
            index = ThreadLocalRandom.current().nextInt(min, max);
        } while (excludedIds.contains(index));
        return index;
    }

    private List<CountryEntity> findRandom(final List<CountryEntity> countries, final int excludedIndex) {
        final Set<Integer> excludedIndexes = new HashSet<>();
        excludedIndexes.add(excludedIndex);
        final IndexRange range = range(excludedIndex, countries.size());
        final List<CountryEntity> otherCountries = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_DISTRACTORS; i++) {
            final int idx = getRandomIndex(range.getMin(), range.getMax(), excludedIndexes);
            excludedIndexes.add(idx);
            otherCountries.add(countries.get(idx));
        }
        return otherCountries;
    }

    protected abstract Set<Integer> exclude(int size);

    protected abstract IndexRange range(int index, int maxResult);

}
