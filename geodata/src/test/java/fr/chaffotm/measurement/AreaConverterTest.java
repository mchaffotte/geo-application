package fr.chaffotm.measurement;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static fr.chaffotm.measurement.AreaUnits.*;
import static org.assertj.core.api.Assertions.assertThat;

public class AreaConverterTest {

    @Test
    @DisplayName("should convert square meter to square hectometer")
    public void convertSquareMeterToSquareHectometer() {
        final AreaConverter converter = new AreaConverter();

        final double convert = converter.convert(100, SQUARE_METER, SQUARE_HECTOMETER);

        assertThat(convert).isEqualTo(0.01);
    }

    @Test
    @DisplayName("should convert square hectometer to square meter")
    public void convertSquareHectometerToSquareMeter() {
        final AreaConverter converter = new AreaConverter();

        final double convert = converter.convert(100, SQUARE_HECTOMETER, SQUARE_METER);

        assertThat(convert).isEqualTo(1000000);
    }

    @Test
    @DisplayName("should convert square kilometer to square meter")
    public void convertSquareKilometerToSquareMeter() {
        final AreaConverter converter = new AreaConverter();

        final double convert = converter.convert(1, SQUARE_KILOMETER, SQUARE_METER);

        assertThat(convert).isEqualTo(1000000);
    }

    @Test
    @DisplayName("should convert square meter to square centimeter")
    public void convertSquareMeterToSquareCentimeter() {
        final AreaConverter converter = new AreaConverter();

        final double convert = converter.convert(1, SQUARE_METER, SQUARE_CENTIMETER);

        assertThat(convert).isEqualTo(10000);
    }

    @Test
    @DisplayName("should convert square centimeter to square millimeter")
    public void convertSquareCentimeterToSquareMillimeter() {
        final AreaConverter converter = new AreaConverter();

        final double convert = converter.convert(1, SQUARE_CENTIMETER, SQUARE_MILLIMETER);

        assertThat(convert).isEqualTo(100);
    }

    @Test
    @DisplayName("should convert square foot to square meter")
    public void convertSquareFootToSquareMeter() {
        final AreaConverter converter = new AreaConverter();

        final double convert = converter.convert(1, SQUARE_FOOT, SQUARE_METER);

        assertThat(convert).isEqualTo(0.09290304);
    }

    @Test
    @DisplayName("should convert square meter to square foot")
    public void convertSquareMeterToSquareFoot() {
        final AreaConverter converter = new AreaConverter();

        final double convert = converter.convert(1, SQUARE_METER, SQUARE_FOOT);

        assertThat(convert).isEqualTo(10.76391041671);
    }

    @Test
    @DisplayName("should convert square meter to centiare")
    public void convertSquareMeterToCentiare() {
        final AreaConverter converter = new AreaConverter();

        final double convert = converter.convert(100, SQUARE_METER, CENTIARE);

        assertThat(convert).isEqualTo(100);
    }

    @Test
    @DisplayName("should convert square meter to are")
    public void convertSquareMeterToAre() {
        final AreaConverter converter = new AreaConverter();

        final double convert = converter.convert(100, SQUARE_METER, ARE);

        assertThat(convert).isEqualTo(1);
    }

    @Test
    @DisplayName("should convert square meter to hectare")
    public void convertSquareMeterToHectare() {
        final AreaConverter converter = new AreaConverter();

        final double convert = converter.convert(100, SQUARE_METER, HECTARE);

        assertThat(convert).isEqualTo(0.01);
    }

    @Test
    @DisplayName("should convert square meter to square decameter")
    public void convertSquareMeterToSquareDecameter() {
        final AreaConverter converter = new AreaConverter();

        final double convert = converter.convert(100, SQUARE_METER, SQUARE_DECAMETER);

        assertThat(convert).isEqualTo(1);
    }

    @Test
    @DisplayName("should convert square meter to square decimeter")
    public void convertSquareMeterToSquareDecimeter() {
        final AreaConverter converter = new AreaConverter();

        final double convert = converter.convert(100, SQUARE_METER, SQUARE_DECIMETER);

        assertThat(convert).isEqualTo(10000);
    }

}
