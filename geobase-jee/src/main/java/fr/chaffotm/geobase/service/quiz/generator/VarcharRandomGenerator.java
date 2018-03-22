package fr.chaffotm.geobase.service.quiz.generator;

import fr.chaffotm.geobase.repository.Order;

import java.util.Collections;
import java.util.Set;

public class VarcharRandomGenerator extends RandomGenerator {

    @Override
    protected Set<Integer> exclude(int size) {
        return Collections.emptySet();
    }

    @Override
    protected IndexRange range(int index, int maxResult) {
        return new IndexRange(0, maxResult);
    }

}