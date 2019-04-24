package fr.chaffotm.query;

import fr.chaffotm.query.criteria.FieldOrder;
import fr.chaffotm.query.criteria.Sort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class SortConverterTest {

    @Test
    @DisplayName("getAsList should get the list with the property in asc order")
    public void getAsListShouldGetTheListWithThePropertyInAscOrder() {
        final SortConverter converter = new SortConverter();

        final List<Sort> sorts = converter.getAsList("name");

        assertThat(sorts)
                .containsExactly(
                        new Sort("name", FieldOrder.ASC));
    }

    @Test
    @DisplayName("getAsList should get the list with 1name asc")
    public void getAsListShouldGetTheListWith1nameAsc() {
        final SortConverter converter = new SortConverter();

        final List<Sort> sorts = converter.getAsList("1name");

        assertThat(sorts)
                .containsExactly(
                        new Sort("1name", FieldOrder.ASC));
    }

    @Test
    @DisplayName("getAsList should get the list with name in uppercase")
    public void getAsListShouldGetTheListWithNameInUppercase() {
        final SortConverter converter = new SortConverter();

        final List<Sort> sorts = converter.getAsList("NAME");

        assertThat(sorts)
                .containsExactly(
                        new Sort("NAME", FieldOrder.ASC));
    }

    @Test
    @DisplayName("getAsList should get the list with name desc")
    public void getAsListShouldGetTheListWithNameDesc() {
        final SortConverter converter = new SortConverter();

        final List<Sort> sorts = converter.getAsList("-name");

        assertThat(sorts)
                .containsExactly(
                        new Sort("name", FieldOrder.DESC));
    }

    @Test
    @DisplayName("getAsList should get the list with name asc without whitespaces")
    public void getAsListShouldGetTheListWithNameAscWithoutWhitespaces() {
        final SortConverter converter = new SortConverter();

        final List<Sort> sorts = converter.getAsList("   name    ");

        assertThat(sorts)
                .containsExactly(
                        new Sort("name", FieldOrder.ASC));
    }

    @Test
    @DisplayName("getAsList should get the list with several properties")
    public void getAsListShouldGetTheListWithSeveralProperties() {
        final SortConverter converter = new SortConverter();

        final List<Sort> sorts = converter.getAsList("name | -age  | surname  ");

        assertThat(sorts)
                .containsExactly(
                        new Sort("name", FieldOrder.ASC),
                        new Sort("age", FieldOrder.DESC),
                        new Sort("surname", FieldOrder.ASC));
    }

    @Test
    @DisplayName("getAsList should not validate the expression two properties without pipe")
    public void getAsListShouldNotValidateTheExpressionTwoPropertiesWithoutPipe() {
        final SortConverter converter = new SortConverter();

        final Throwable thrown = catchThrowable(() -> converter.getAsList("name age"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("getAsList should not validate the expression property with a pipe but nothing after")
    public void getAsListShouldNotValidateTheExpressionPropertyWithAPipeButNothingAfter() {
        final SortConverter converter = new SortConverter();

        final Throwable thrown = catchThrowable(() -> converter.getAsList("name |"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("getAsList should not validate the expression property with plus prefix")
    public void getAsListShouldNotValidateTheExpressionPropertyWithPlusPrefix() {
        final SortConverter converter = new SortConverter();

        final Throwable thrown = catchThrowable(() -> converter.getAsList("+name"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("getAsList should get the empty list with empty expression")
    public void getAsListShouldGetTheEmptyListWithEmptyExpression() {
        final SortConverter converter = new SortConverter();

        final List<Sort> sorts = converter.getAsList("");

        assertThat(sorts).isEmpty();
    }

    @Test
    @DisplayName("getAsList should get the empty list with null expression")
    public void getAsListShouldGetTheEmptyListWithNullExpression() {
        final SortConverter converter = new SortConverter();

        final List<Sort> sorts = converter.getAsList(null);

        assertThat(sorts).isEmpty();
    }

    @Test
    @DisplayName("getAsList should get the empty list with whitespaces expression")
    public void getAsListShouldGetTheEmptyListWithWhitespacesExpression() {
        final SortConverter converter = new SortConverter();

        final List<Sort> sorts = converter.getAsList("      ");

        assertThat(sorts).isEmpty();
    }

}
