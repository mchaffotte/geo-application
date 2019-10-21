package fr.chaffotm.quizzify.service.generator;

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
    public <T> List<MultipleChoice<T>> generate(final List<T> entities, final AnswerType answerType) {
        final int size = entities.size();
        final Set<Integer> excludeIds = new HashSet<>(exclude(size));
        final List<MultipleChoice<T>> multipleChoices = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_QUESTIONS; i++) {
            final int entityIndex = getRandomIndex(size, excludeIds);
            excludeIds.add(entityIndex);
            final T answer = entities.get(entityIndex);
            final MultipleChoice<T> multipleChoice = new MultipleChoice<>(answer);
            if (AnswerType.MULTIPLE_CHOICE == answerType) {
                final List<T> distractors = findRandom(entities, entityIndex);
                for (T distractor : distractors) {
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

    private <T> List<T> findRandom(final List<T> entities, final int excludedIndex) {
        final Set<Integer> excludedIndexes = new HashSet<>();
        excludedIndexes.add(excludedIndex);
        final IndexRange range = range(excludedIndex, entities.size());
        final List<T> otherEntities = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_DISTRACTORS; i++) {
            final int idx = getRandomIndex(range.getMin(), range.getMax(), excludedIndexes);
            excludedIndexes.add(idx);
            otherEntities.add(entities.get(idx));
        }
        return otherEntities;
    }

    protected abstract Set<Integer> exclude(int size);

    protected abstract IndexRange range(int index, int maxResult);

}
