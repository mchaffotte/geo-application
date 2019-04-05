package fr.chaffotm.query;

import fr.chaffotm.query.criteria.FieldOrder;
import fr.chaffotm.query.criteria.QueryCriteria;
import fr.chaffotm.query.domain.Element;
import fr.chaffotm.query.domain.ElementCategory;
import fr.chaffotm.query.domain.ElementSymbol;
import fr.chaffotm.query.jpa.JPACriteriaRepository;
import fr.chaffotm.query.transaction.TransactionManager;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

import static fr.chaffotm.query.criteria.Functions.sum;
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

    @BeforeClass
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
            categories.put(build("Alkali metal"), Collections.singletonList(ElementSymbol.LI));
            categories.put(build("Alkaline earth metal"), Collections.singletonList(ElementSymbol.BE));
            categories.put(build("Noble gas"), Arrays.asList(ElementSymbol.HE, ElementSymbol.NE));
            categories.put(build("Metalloid"), Collections.singletonList(ElementSymbol.B));
            categories.put(build("Reactive nonmetal"), Arrays.asList(ElementSymbol.H, ElementSymbol.C,
                    ElementSymbol.N, ElementSymbol.O, ElementSymbol.F));
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
    public void criteria_should_sort_the_elements_in_descending_order() {
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
    public void criteria_should_sort_the_elements_in_ascending_order() {
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
    public void criteria_should_sort_the_elements_in_ascending_order_without_defining_the_order() {
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
    public void sort_should_sort_the_elements_in_ascending_order_by_default() {
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
    public void sort_should_not_accept_an_unknown_field() {
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
    public void sort_should_not_return_elements_using_a_null_field() {
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
    public void join_and_sort_should_sort_the_elements_in_ascending_order() {
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
    public void sort_should_join_the_missing_relation() {
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
    public void join_and_sort_should_sort_the_elements_in_ascending_order3() {
        final List<Element> elements = TRANSACTION_MANAGER.execute(em -> {
            final CriteriaRepository repository = new JPACriteriaRepository(em);
            final QueryCriteria<Element> criteria = new QueryCriteria<>(Element.class);
            criteria.setFunction(sum("mass_number", "atomicNumber", "numberOfNeutrons"));
            criteria.addSort("mass_number");
            criteria.addSort("name", FieldOrder.DESC);

            return repository.findAll(1, null, criteria);
        });

        assertThat(elements).extracting("name")
                .containsExactly("Hydrogen", "Helium", "Deuterium", "Lithium", "Beryllium", "Boron", "Carbon", "Nitrogen",
                        "Oxygen", "Fluorine", "Neon");
    }

}
