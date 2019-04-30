package fr.chaffotm.querify.jpa.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "elements")
public class Element {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "element_generator")
    @SequenceGenerator(name="element_generator", sequenceName = "element_sequence", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 25)
    private String name;

    @Column(name = "symbol", nullable = false, length = 2)
    private String symbol;

    private int atomicNumber;

    private int numberOfNeutrons;

    @ManyToOne
    @JoinColumn
    private ElementCategory category;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getAtomicNumber() {
        return atomicNumber;
    }

    public void setAtomicNumber(int atomicNumber) {
        this.atomicNumber = atomicNumber;
    }

    public int getNumberOfNeutrons() {
        return numberOfNeutrons;
    }

    public void setNumberOfNeutrons(int numberOfNeutrons) {
        this.numberOfNeutrons = numberOfNeutrons;
    }

    public ElementCategory getCategory() {
        return category;
    }

    public void setCategory(ElementCategory category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Element element = (Element) o;
        return atomicNumber == element.atomicNumber &&
                numberOfNeutrons == element.numberOfNeutrons &&
                Objects.equals(symbol, element.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, atomicNumber, numberOfNeutrons);
    }

}
