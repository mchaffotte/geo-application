package fr.chaffotm.querify;

import fr.chaffotm.querify.criteria.FieldOrder;
import fr.chaffotm.querify.criteria.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SortConverter {

    private static final String PROPERTY_REGEXP = "\\s*(-)?\\w+\\s*";

    private static final String DELIMITER_REGEXP = "\\s*\\|\\s*";

    private static final String GRAMMAR_PATTERN = PROPERTY_REGEXP + "(" + DELIMITER_REGEXP + PROPERTY_REGEXP + ")*";

    private static final Pattern DELIMITER_PATTERN = Pattern.compile(DELIMITER_REGEXP);

    private static final Pattern VALIDATION_PATTERN = Pattern.compile(GRAMMAR_PATTERN);

    public List<Sort> getAsList(final String sortExpression) {
        if (sortExpression == null) {
            return List.of();
        }
        final String trimExpression = sortExpression.trim();
        if (trimExpression.isEmpty()) {
            return List.of();
        }
        return buildList(trimExpression);
    }

    private List<Sort> buildList(final String sortExpression) {
        validate(sortExpression);
        final List<Sort> sorts = new ArrayList<>();
        final String[] attributes = DELIMITER_PATTERN.split(sortExpression);
        for (final String attribute : attributes) {
            sorts.add(build(attribute));
        }
        return sorts;
    }

    private void validate(final String sort) {
        final Matcher matcher = VALIDATION_PATTERN.matcher(sort);
        if (!matcher.matches()) {
            throw new IllegalArgumentException();
        }
    }

    private Sort build(final String property) {
        final FieldOrder order;
        final String element;
        if (property.startsWith("-")) {
            element = property.substring(1);
            order = FieldOrder.DESC;
        } else {
            element = property;
            order = FieldOrder.ASC;
        }
        return new Sort(element, order);
    }

}
