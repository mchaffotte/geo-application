package fr.chaffotm.query.transaction;

import javax.persistence.EntityManager;

public interface ReturnTransaction<T> {

    T execute(EntityManager entityManager);

}
