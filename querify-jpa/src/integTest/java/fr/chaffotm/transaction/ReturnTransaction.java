package fr.chaffotm.transaction;

import javax.persistence.EntityManager;

@FunctionalInterface
public interface ReturnTransaction<T> {

    T execute(EntityManager entityManager);

}
