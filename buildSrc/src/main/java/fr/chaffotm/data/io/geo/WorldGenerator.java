package fr.chaffotm.data.io.geo;

import org.slf4j.Logger;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class WorldGenerator {

    private final Logger logger;

    private final Jsonb jsonb;

    private final Validator validator;

    public WorldGenerator(final Logger logger) {
        this.logger = logger;
        jsonb = JsonbBuilder.create();
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public String toSQL() throws IOException {
        final World world = getWorld();
        final String violations = validate(world);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException(violations);
        }
        displayWarnings(world);
        return toSQL(world);
    }

    private World getWorld() throws IOException {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream("world.json")) {
            return jsonb.fromJson(inputStream, World.class);
        }
    }

    private String validate(final World world) {
        final Set<ConstraintViolation<World>> violations = validator.validate(world);
        final StringBuilder violationBuilder = new StringBuilder();
        for (ConstraintViolation<World> violation : violations) {
            violationBuilder.append(violation.getRootBeanClass().getSimpleName()).append(".")
                    .append(violation.getPropertyPath()).append(".")
                    .append(violation.getMessage()).append("\\n");
        }
        return violationBuilder.toString();
    }

    private String getCountryBorderCode(String countryCode, String borderCode) {
        final String code;
        if (countryCode.compareTo(borderCode) < 0) {
            code = countryCode + borderCode;
        } else {
            code = borderCode + countryCode;
        }
        return code;
    }

    private void displayWarnings(final World world) throws IOException {
        final Map<String, Double> borders = new HashMap<>();
        for (Country country : world.getCountries()) {
            checkImages(country);
            for (Border border : country.getBorders()) {
                final String code = getCountryBorderCode(country.getAlpha3Code(), border.getCountryCode());
                final Double distance = borders.get(code);
                if (distance == null) {
                    borders.put(code, border.getLength());
                } else {
                    if (distance != border.getLength()) {
                        logger.warn(code + " " + distance + "!=" + border.getLength());
                    }
                    borders.remove(code);
                }
            }
        }
        for (Map.Entry<String, Double> border : borders.entrySet()) {
            logger.warn("Missing other border: " + border.getKey() + " " + border.getValue());
        }
    }

    private void checkImages(Country country) throws IOException {
        checkImage(country, "flags");
        checkImage(country, "silhouettes");
    }

    private void checkImage(final Country country, final String resource) throws IOException {
        final ClassLoader classLoader = WorldGenerator.class.getClassLoader();
        try (InputStream stream = classLoader.getResourceAsStream(resource + "/" + country.getAlpha2Code().toLowerCase() + ".png")) {
            if (stream == null) {
                logger.warn("No image of " + resource + " for " + country.getAlpha2Code() + "(" + country.getName() + ")");
            }
        }
    }

    private int getIndex(final List<Region> regions, int numericCode) {
        int index = 1;
        for (Region region : regions) {
            if (region.getNumericCode() == numericCode) {
                return index;
            }
            index++;
        }
        return -1;
    }

    private String toSQL(final World world) {
        final List<Region> regions = world.getRegions();
        final StringBuilder regionBuilder = new StringBuilder();
        int regionIndex = 1;
        for (Region region : regions) {
            regionBuilder.append("INSERT INTO region (id, numeric_code, name, parent_id) VALUES (");
            regionBuilder.append(regionIndex).append(", ");
            regionBuilder.append(region.getNumericCode()).append(", '");
            regionBuilder.append(region.getName()).append("', ");
            if (region.getParents().isEmpty()) {
                regionBuilder.append("null");
            } else {
                final Integer firstRegion = region.getParents().get(0);
                regionBuilder.append(getIndex(regions, firstRegion));
            }
            regionBuilder.append(");\n");
            regionIndex++;
        }

        final List<Country> countries = world.getCountries();
        countries.sort(Comparator.comparing(Country::getAlpha3Code));

        final StringBuilder countryBuilder = new StringBuilder();
        final StringBuilder cityBuilder = new StringBuilder();
        int countryIndex = 1;
        int cityIndex = 1;
        for (final Country country : countries) {
            final Area area = country.getArea();
            final double landArea = area.getLand() * 100;
            final double waterArea = area.getWater() * 100;
            countryBuilder.append("INSERT INTO country (id, code, name, land_area, water_area, region_id, capital_id) VALUES (")
                    .append(countryIndex).append(", '")
                    .append(country.getAlpha2Code()).append("', '")
                    .append(country.getName().replaceAll("'", "''")).append("', ")
                    .append(landArea).append(", ")
                    .append(waterArea).append(", ")
                    .append(getIndex(regions, country.getRegion())).append(", ");
            final String capital = country.getCapital();
            if (capital == null || capital.isBlank()) {
                countryBuilder.append("null);\n");
            } else {
                countryBuilder.append(cityIndex).append(");\n");
                cityBuilder.append("INSERT INTO city (id, name) VALUES (").append(cityIndex).append(", '").append(capital.replaceAll("'", "''")).append("');\n");
                cityIndex++;
            }
            countryIndex++;
        }
        return regionBuilder.toString() + "\n" + cityBuilder.toString() + "\n" + countryBuilder.toString() + "\n" +
                "ALTER SEQUENCE region_sequence RESTART WITH " + regionIndex + ";\n" +
                "ALTER SEQUENCE city_sequence RESTART WITH " + cityIndex + ";\n" +
                "ALTER SEQUENCE country_sequence RESTART WITH " + countryIndex + ";\n";
    }

}
