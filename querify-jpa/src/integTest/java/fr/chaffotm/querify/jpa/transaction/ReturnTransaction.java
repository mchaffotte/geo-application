package fr.chaffotm.querify.jpa.transaction;

import javax.persistence.EntityManager;

@FunctionalInterface
public interface ReturnTransaction<T> {

    T execute(EntityManager entityManager);

}
