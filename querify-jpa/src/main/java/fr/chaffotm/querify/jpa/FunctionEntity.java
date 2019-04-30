package fr.chaffotm.querify.jpa;

public class FunctionEntity {

    private final Object entity;

    private final double value;

    public FunctionEntity(final Object entity) {
        this(entity, 0);
    }

    public FunctionEntity(final Object entity, final double value) {
        this.entity = entity;
        this.value = value;
    }

    public FunctionEntity(final Object entity, final int value) {
        this.entity = entity;
        this.value = value;
    }

    public <T> T getEntity(final Class<T> entityClass) {
        return entityClass.cast(entity);
    }

    public double getValue() {
        return value;
    }

}
