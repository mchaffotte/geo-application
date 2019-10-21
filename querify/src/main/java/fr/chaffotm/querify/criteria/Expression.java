package fr.chaffotm.querify.criteria;

public interface Expression {

    void accept(ExpressionVisitor visitor);
}
