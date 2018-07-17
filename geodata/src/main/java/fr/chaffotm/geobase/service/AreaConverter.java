package fr.chaffotm.geobase.service;

public final class AreaConverter {

    private AreaConverter() {
        // no
    }

    public static double convertSquareHectometerToSquareKilometer(final int squareHectometers) {
        return squareHectometers / 100.0;
    }

    public static int convertSquareKilometerToSquareHectometer(final double squareKilometers) {
        return (int) (squareKilometers * 100);
    }

}
