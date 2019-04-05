package fr.chaffotm.query.transaction;

import javax.persistence.EntityManager;

public interface Transaction {

    void execute(EntityManager entityManager);

}
