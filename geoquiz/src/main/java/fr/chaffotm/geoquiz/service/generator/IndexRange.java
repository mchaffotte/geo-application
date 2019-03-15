package fr.chaffotm.geoquiz.service.generator;

public class IndexRange {

    private int min;

    private int max;

    public IndexRange(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

}