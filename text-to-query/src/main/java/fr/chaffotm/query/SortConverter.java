package fr.chaffotm.query;

import fr.chaffotm.query.criteria.Order;
import fr.chaffotm.query.criteria.Sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SortConverter {

    private static final String PROPERTY_PATTERN = "\\s*(-)?\\w+\\s*";

    private static final String DELIMITER_PATTERN = "\\s*\\|\\s*";

    private static final String GRAMMAR_PATTERN = PROPERTY_PATTERN + "(" + DELIMITER_PATTERN + PROPERTY_PATTERN + ")*";

    private static final Pattern VALIDATION_PATTERN = Pattern.compile(GRAMMAR_PATTERN);

    public List<Sort> getAsList(final String sortExpression) {
        if (sortExpression == null) {
            return Collections.emptyList();
        }
        final String trimExpression = sortExpression.trim();
        if (trimExpression.isEmpty()) {
            return Collections.emptyList();
        }
        return buildList(trimExpression);
    }

    private List<Sort> buildList(final String sortExpression) {
        validate(sortExpression);
        final List<Sort> sorts = new ArrayList<>();
        final String[] attributes = sortExpression.split(DELIMITER_PATTERN);
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
        final Order order;
        final String element;
        if (property.startsWith("-")) {
            element = property.substring(1);
            order = Order.DESC;
        } else {
            element = property;
            order = Order.ASC;
        }
        return new Sort(element, order);
    }

}
