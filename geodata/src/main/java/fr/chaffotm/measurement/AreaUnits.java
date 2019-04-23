package fr.chaffotm.measurement;

public final class AreaUnits {

    public static final Unit SQUARE_KILOMETER = new Unit("square kilometer", "km²", 1000);

    public static final Unit SQUARE_HECTOMETER = new Unit("square hectometer", "hm²", 100);

    public static final Unit SQUARE_DECAMETER = new Unit("square decameter", "dam²", 10);

    public static final Unit SQUARE_METER = new Unit("square meter", "m²", 1);

    public static final Unit SQUARE_DECIMETER = new Unit("square decimeter", "dm²",  0.1);

    public static final Unit SQUARE_CENTIMETER = new Unit("square centimeter", "cm²", 0.01);

    public static final Unit SQUARE_MILLIMETER = new Unit("square millimeter", "m²", 0.001);

    public static final Unit CENTIARE = new Unit("centiare", "ca", 1);

    public static final Unit ARE = new Unit("are", "a", 10 );

    public static final Unit HECTARE = new Unit("hectare", "ha", 100);

    public static final Unit SQUARE_FOOT = new Unit("foot", "square foot", 0.3048);

    private AreaUnits() {
    }

}
