package fr.chaffotm.geoquiz.service.generator;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SuperiorityRandomGenerator extends RandomGenerator {

    @Override
    protected Set<Integer> exclude(int size) {
        return Stream.of(size -1, size -2, size -3).collect(Collectors.toSet());
    }

    @Override
    protected IndexRange range(int index, int maxResult) {
        return new IndexRange(index + 1, maxResult);
    }

}
