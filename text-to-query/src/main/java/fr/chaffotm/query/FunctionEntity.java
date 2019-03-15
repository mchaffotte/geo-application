package fr.chaffotm.query;

public class FunctionEntity {

    private Object entity;

    private double value;

    public FunctionEntity(Object entity, double value) {
        this.entity = entity;
        this.value = value;
    }

    public FunctionEntity(Object entity, int value) {
        this.entity = entity;
        this.value = value;
    }

    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
