package fr.chaffotm.transaction;

import javax.persistence.EntityManager;

@FunctionalInterface
public interface Transaction {

    void execute(EntityManager entityManager);

}
