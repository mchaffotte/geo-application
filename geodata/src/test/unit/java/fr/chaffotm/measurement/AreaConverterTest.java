package fr.chaffotm.measurement;

import org.junit.Test;

import static fr.chaffotm.measurement.AreaUnits.*;
import static org.assertj.core.api.Assertions.assertThat;

public class AreaConverterTest {

    @Test
    public void convertFromSquareMeterToSquareHectometer() {
        final AreaConverter converter = new AreaConverter();

        final double convert = converter.convert(100, SQUARE_METER, SQUARE_HECTOMETER);

        assertThat(convert).isEqualTo(0.01);
    }

    @Test
    public void convertFromSquareHectometerToSquareMeter() {
        final AreaConverter converter = new AreaConverter();

        final double convert = converter.convert(100, SQUARE_HECTOMETER, SQUARE_METER);

        assertThat(convert).isEqualTo(1000000);
    }

    @Test
    public void convertFromSquareKilometerToSquareMeter() {
        final AreaConverter converter = new AreaConverter();

        final double convert = converter.convert(1, SQUARE_KILOMETER, SQUARE_METER);

        assertThat(convert).isEqualTo(1000000);
    }

    @Test
    public void convertFromSquareMeterToSquareCentimeter() {
        final AreaConverter converter = new AreaConverter();

        final double convert = converter.convert(1, SQUARE_METER, SQUARE_CENTIMETER);

        assertThat(convert).isEqualTo(10000);
    }

    @Test
    public void convertFromSquareCentimeterToSquareMillimeter() {
        final AreaConverter converter = new AreaConverter();

        final double convert = converter.convert(1, SQUARE_CENTIMETER, SQUARE_MILLIMETER);

        assertThat(convert).isEqualTo(100);
    }

    @Test
    public void convertFromSquareFootToSquareMeter() {
        final AreaConverter converter = new AreaConverter();

        final double convert = converter.convert(1, SQUARE_FOOT, SQUARE_METER);

        assertThat(convert).isEqualTo(0.09290304);
    }

    @Test
    public void convertFromSquareMeterToSquareFoot() {
        final AreaConverter converter = new AreaConverter();

        final double convert = converter.convert(1, SQUARE_METER, SQUARE_FOOT);

        assertThat(convert).isEqualTo(10.76391041671);
    }

    @Test
    public void convertFromSquareMeterToCentiare() {
        final AreaConverter converter = new AreaConverter();

        final double convert = converter.convert(100, SQUARE_METER, CENTIARE);

        assertThat(convert).isEqualTo(100);
    }

    @Test
    public void convertFromSquareMeterToAre() {
        final AreaConverter converter = new AreaConverter();

        final double convert = converter.convert(100, SQUARE_METER, ARE);

        assertThat(convert).isEqualTo(1);
    }

    @Test
    public void convertFromSquareMeterToHectare() {
        final AreaConverter converter = new AreaConverter();

        final double convert = converter.convert(100, SQUARE_METER, HECTARE);

        assertThat(convert).isEqualTo(0.01);
    }

    @Test
    public void convertFromSquareMeterToSquareDecameter() {
        final AreaConverter converter = new AreaConverter();

        final double convert = converter.convert(100, SQUARE_METER, SQUARE_DECAMETER);

        assertThat(convert).isEqualTo(1);
    }

    @Test
    public void convertFromSquareMeterToSquareDecimeter() {
        final AreaConverter converter = new AreaConverter();

        final double convert = converter.convert(100, SQUARE_METER, SQUARE_DECIMETER);

        assertThat(convert).isEqualTo(10000);
    }

}