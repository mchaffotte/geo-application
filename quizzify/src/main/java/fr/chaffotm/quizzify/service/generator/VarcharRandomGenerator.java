package fr.chaffotm.quizzify.service.generator;

import java.util.Set;

public class VarcharRandomGenerator extends RandomGenerator {

    @Override
    protected Set<Integer> exclude(int size) {
        return Set.of();
    }

    @Override
    protected IndexRange range(int index, int maxResult) {
        return new IndexRange(0, maxResult);
    }

}