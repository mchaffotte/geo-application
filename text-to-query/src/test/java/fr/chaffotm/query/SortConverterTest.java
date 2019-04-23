package fr.chaffotm.query;

import fr.chaffotm.query.criteria.FieldOrder;
import fr.chaffotm.query.criteria.Sort;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class SortConverterTest {

    @Test
    public void getAsList_should_get_the_list_with_the_property_in_asc_order() {
        final SortConverter converter = new SortConverter();

        final List<Sort> sorts = converter.getAsList("name");

        assertThat(sorts)
                .containsExactly(
                        new Sort("name", FieldOrder.ASC));
    }

    @Test
    public void getAsList_should_get_the_list_with_1name_asc() {
        final SortConverter converter = new SortConverter();

        final List<Sort> sorts = converter.getAsList("1name");

        assertThat(sorts)
                .containsExactly(
                        new Sort("1name", FieldOrder.ASC));
    }

    @Test
    public void getAsList_should_get_the_list_with_name_in_uppercase() {
        final SortConverter converter = new SortConverter();

        final List<Sort> sorts = converter.getAsList("NAME");

        assertThat(sorts)
                .containsExactly(
                        new Sort("NAME", FieldOrder.ASC));
    }

    @Test
    public void getAsList_should_get_the_list_with_name_desc() {
        final SortConverter converter = new SortConverter();

        final List<Sort> sorts = converter.getAsList("-name");

        assertThat(sorts)
                .containsExactly(
                        new Sort("name", FieldOrder.DESC));
    }

    @Test
    public void getAsList_should_get_the_list_with_name_asc_without_whitespaces() {
        final SortConverter converter = new SortConverter();

        final List<Sort> sorts = converter.getAsList("   name    ");

        assertThat(sorts)
                .containsExactly(
                        new Sort("name", FieldOrder.ASC));
    }

    @Test
    public void getAsList_should_get_the_list_with_several_properties() {
        final SortConverter converter = new SortConverter();

        final List<Sort> sorts = converter.getAsList("name | -age  | surname  ");

        assertThat(sorts)
                .containsExactly(
                        new Sort("name", FieldOrder.ASC),
                        new Sort("age", FieldOrder.DESC),
                        new Sort("surname", FieldOrder.ASC));
    }

    @Test
    public void getAsList_should_not_validate_the_expression_two_properties_without_pipe() {
        final SortConverter converter = new SortConverter();

        final Throwable thrown = catchThrowable(() -> converter.getAsList("name age"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void getAsList_should_not_validate_the_expression_property_with_a_pipe_but_nothing_after() {
        final SortConverter converter = new SortConverter();

        final Throwable thrown = catchThrowable(() -> converter.getAsList("name |"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void getAsList_should_not_validate_the_expression_property_with_plus_prefix() {
        final SortConverter converter = new SortConverter();

        final Throwable thrown = catchThrowable(() -> converter.getAsList("+name"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void getAsList_should_get_the_empty_list_with_empty_expression() {
        final SortConverter converter = new SortConverter();

        final List<Sort> sorts = converter.getAsList("");

        assertThat(sorts).isEmpty();
    }

    @Test
    public void getAsList_should_get_the_empty_list_with_null_expression() {
        final SortConverter converter = new SortConverter();

        final List<Sort> sorts = converter.getAsList(null);

        assertThat(sorts).isEmpty();
    }

    @Test
    public void getAsList_should_get_the_empty_list_with_whitespaces_expression() {
        final SortConverter converter = new SortConverter();

        final List<Sort> sorts = converter.getAsList("      ");

        assertThat(sorts).isEmpty();
    }

}