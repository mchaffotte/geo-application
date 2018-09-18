package fr.chaffotm.measurement;

public class Unit {

    private final String name;

    private final String symbol;

    private final String factor;

    public Unit(final String name, final String symbol, double factor) {
        this.name = name;
        this.symbol = symbol;
        this.factor = String.valueOf(factor * factor);
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getFactor() {
        return factor;
    }

}
