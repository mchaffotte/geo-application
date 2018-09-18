package fr.chaffotm.measurement;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AreaConverter {

    private int round;

    public AreaConverter() {
        this(12);
    }

    public AreaConverter(final int round) {
        this.round = round;
    }

    public double convert(final double value, final Unit from, final Unit to) {
        final BigDecimal valueDecimal = new BigDecimal(String.valueOf(value));
        final BigDecimal fromDecimal = new BigDecimal(from.getFactor());
        final BigDecimal toDecimal = new BigDecimal(to.getFactor());
        return valueDecimal.multiply(fromDecimal.divide(toDecimal, round, RoundingMode.CEILING)).doubleValue();
    }

}
