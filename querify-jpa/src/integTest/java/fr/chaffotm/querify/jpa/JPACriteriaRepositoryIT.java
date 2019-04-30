package fr.chaffotm.querify.jpa;

import fr.chaffotm.querify.CriteriaRepository;
import fr.chaffotm.querify.criteria.FieldOrder;
import fr.chaffotm.querify.criteria.QueryCriteria;
import fr.chaffotm.querify.jpa.domain.Element;
import fr.chaffotm.querify.jpa.domain.ElementCategory;
import fr.chaffotm.querify.jpa.domain.ElementSymbol;
import fr.chaffotm.querify.jpa.transaction.TransactionManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fr.chaffotm.querify.criteria.Functions.sum;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class JPACriteriaRepositoryIT {

    private static final TransactionManager TRANSACTION_MANAGER = new TransactionManager("test");

    private static ElementCategory build(final String name) {
        final ElementCategory category= new ElementCategory();
        category.setName(name);
        return category;
    }

    private static Element build(final String name, final String symbol, final int atomicNumber) {
        return build(name, symbol, atomicNumber, 0);
    }

    private static Element build(final String name, final String symbol, final int atomicNumber, final int numberOfNeutrons) {
        final Element element = new Element();
        element.setName(name);
        element.setSymbol(symbol);
        element.setAtomicNumber(atomicNumber);
        element.setNumberOfNeutrons(numberOfNeutrons);
        return element;
    }

    @BeforeAll
    public static void setUp() {
        TRANSACTION_MANAGER.execute(em -> {
            final Map<ElementSymbol, Element> elements = new HashMap<>();
            elements.put(ElementSymbol.H, build("Hydrogen", "H", 1));
            elements.put(ElementSymbol.HE, build("Helium", "He", 2));
            elements.put(ElementSymbol.LI, build("Lithium", "Li", 3));
            elements.put(ElementSymbol.BE, build("Beryllium", "Be", 4));
            elements.put(ElementSymbol.B, build("Boron", "B", 5));
            elements.put(ElementSymbol.C, build("Carbon", "C", 6));
            elements.put(ElementSymbol.N, build("Nitrogen", "N", 7));
            elements.put(ElementSymbol.O, build("Oxygen", "O", 8));
            elements.put(ElementSymbol.F, build("Fluorine", "F", 9));
            elements.put(ElementSymbol.NE, build("Neon", "Ne", 10));
            for (Element element : elements.values()) {
                em.persist(element);
            }
            em.persist(build("Deuterium", "D", 1, 1));

            final Map<ElementCategory, List<ElementSymbol>> categories = new HashMap<>();
            categories.put(build("Alkali metal"), List.of(ElementSymbol.LI));
            categories.put(build("Alkaline earth metal"), List.of(ElementSymbol.BE));
            categories.put(build("Noble gas"), List.of(ElementSymbol.HE, ElementSymbol.NE));
            categories.put(build("Metalloid"), List.of(ElementSymbol.B));
            categories.put(build("Reactive nonmetal"), List.of(ElementSymbol.H, ElementSymbol.C, ElementSymbol.N,
                    ElementSymbol.O, ElementSymbol.F));
            for (Map.Entry<ElementCategory, List<ElementSymbol>> category : categories.entrySet()) {
                final ElementCategory elementCategory = category.getKey();
                for (ElementSymbol symbol : category.getValue()) {
                    final Element element = elements.get(symbol);
                    elementCategory.addElement(element);
                }
                em.persist(elementCategory);
            }
        });
    }

    @Test
    @DisplayName("criteria should sort the elements in descending order")
    public void criteriaShouldSortTheElementsInDescendingOrder() {
        final List<Element> elements = TRANSACTION_MANAGER.execute(em -> {
            final CriteriaRepository repository = new JPACriteriaRepository(em);
            final QueryCriteria<Element> criteria = new QueryCriteria<>(Element.class);
            criteria.addSort("symbol", FieldOrder.DESC);

            return repository.findAll(1, null, criteria);
        });

        assertThat(elements).extracting("name")
                .containsExactly("Oxygen", "Neon", "Nitrogen", "Lithium", "Helium", "Hydrogen", "Fluorine", "Deuterium",
                        "Carbon", "Beryllium", "Boron");
    }

    @Test
    @DisplayName("criteria should sort the elements in ascending order")
    public void criteriaShouldSortTheElementsInAscendingOrder() {
        final List<Element> elements = TRANSACTION_MANAGER.execute(em -> {
            final CriteriaRepository repository = new JPACriteriaRepository(em);
            final QueryCriteria<Element> criteria = new QueryCriteria<>(Element.class);
            criteria.addSort("symbol", FieldOrder.ASC);

            return repository.findAll(1, null, criteria);
        });

        assertThat(elements).extracting("name")
                .containsExactly("Boron", "Beryllium", "Carbon", "Deuterium", "Fluorine", "Hydrogen", "Helium", "Lithium",
                        "Nitrogen", "Neon","Oxygen");
    }

    @Test
    @DisplayName("criteria should sort the elements in ascending order without defining the order")
    public void criteriaShouldSortTheElementsInAscendingOrderWithoutDefiningTheOrder() {
        final List<Element> elements = TRANSACTION_MANAGER.execute(em -> {
            final CriteriaRepository repository = new JPACriteriaRepository(em);
            final QueryCriteria<Element> criteria = new QueryCriteria<>(Element.class);
            criteria.addSort("symbol", null);

            return repository.findAll(1, null, criteria);
        });

        assertThat(elements).extracting("name")
                .containsExactly("Boron", "Beryllium", "Carbon", "Deuterium", "Fluorine", "Hydrogen", "Helium", "Lithium",
                        "Nitrogen", "Neon","Oxygen");
    }

    @Test
    @DisplayName("sort should sort the elements in ascending order by default")
    public void sortShouldSortTheElementsInAscendingOrderByDefault() {
        final List<Element> elements = TRANSACTION_MANAGER.execute(em -> {
            final CriteriaRepository repository = new JPACriteriaRepository(em);
            final QueryCriteria<Element> criteria = new QueryCriteria<>(Element.class);
            criteria.addSort("symbol");

            return repository.findAll(1, null, criteria);
        });

        assertThat(elements).extracting("name")
                .containsExactly("Boron", "Beryllium", "Carbon", "Deuterium", "Fluorine", "Hydrogen", "Helium", "Lithium",
                        "Nitrogen", "Neon","Oxygen");
    }

    @Test
    @DisplayName("sort should not accept an unknown field")
    public void sortSouldNotAcceptAnUnknownField() {
        final Throwable throwable = catchThrowable(() -> TRANSACTION_MANAGER.execute(em -> {
            final CriteriaRepository repository = new JPACriteriaRepository(em);
            final QueryCriteria<Element> criteria = new QueryCriteria<>(Element.class);
            criteria.addSort("weight", FieldOrder.ASC);

            return repository.findAll(1, null, criteria);
        }));

        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("sort should not return elements using a null field")
    public void sortShoulNotReturnElementsUsingANullField() {
        final Throwable throwable = catchThrowable(() -> TRANSACTION_MANAGER.execute(em -> {
            final CriteriaRepository repository = new JPACriteriaRepository(em);
            final QueryCriteria<Element> criteria = new QueryCriteria<>(Element.class);
            criteria.addSort(null, FieldOrder.ASC);

            return repository.findAll(1, null, criteria);
        }));

        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("join and sort should sort the elements in ascending order")
    public void joinAndSortShouldSortTheElementsInAscendingOrder() {
        final List<Element> elements = TRANSACTION_MANAGER.execute(em -> {
            final CriteriaRepository repository = new JPACriteriaRepository(em);
            final QueryCriteria<Element> criteria = new QueryCriteria<>(Element.class);
            criteria.setJoin("category");
            criteria.addSort("category.name");
            criteria.addSort("name");

            return repository.findAll(1, null, criteria);
        });

        assertThat(elements).extracting("name")
                .containsExactly("Lithium", "Beryllium", "Boron", "Helium", "Neon", "Carbon", "Fluorine", "Hydrogen",
                        "Nitrogen",  "Oxygen");
    }

    @Test
    @DisplayName("sort should join the missing relation")
    public void sortShouldJoinTheMissingRelation() {
        final List<Element> elements = TRANSACTION_MANAGER.execute(em -> {
            final CriteriaRepository repository = new JPACriteriaRepository(em);
            final QueryCriteria<Element> criteria = new QueryCriteria<>(Element.class);
            criteria.addSort("category.name");
            criteria.addSort("name");

            return repository.findAll(1, null, criteria);
        });

        assertThat(elements).extracting("name")
                .containsExactly("Lithium", "Beryllium", "Boron", "Helium", "Neon", "Carbon", "Fluorine", "Hydrogen",
                        "Nitrogen",  "Oxygen");
    }

    @Test
    @DisplayName("function and sort should sort the elements in descending order")
    public void functionAndSortShouldSortTheElementsInDescendingOrder() {
        final List<Element> elements = TRANSACTION_MANAGER.execute(em -> {
            final CriteriaRepository repository = new JPACriteriaRepository(em);
            final QueryCriteria<Element> criteria = new QueryCriteria<>(Element.class);
            criteria.setFunction(sum("mass number", "atomicNumber", "numberOfNeutrons"));
            criteria.addSort("mass number");
            criteria.addSort("name", FieldOrder.DESC);

            return repository.findAll(1, null, criteria);
        });

        assertThat(elements).extracting("name")
                .containsExactly("Hydrogen", "Helium", "Deuterium", "Lithium", "Beryllium", "Boron", "Carbon", "Nitrogen",
                        "Oxygen", "Fluorine", "Neon");
    }

}
