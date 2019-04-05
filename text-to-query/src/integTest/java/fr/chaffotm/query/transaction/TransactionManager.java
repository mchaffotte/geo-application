package fr.chaffotm.query.transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class TransactionManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(Transaction.class);

    private final EntityManagerFactory factory;

    public TransactionManager(final String persistenceUnitName) {
        factory = Persistence.createEntityManagerFactory(persistenceUnitName);
    }

    public <T> T execute(final ReturnTransaction<T> transaction) {
        final EntityManager em = factory.createEntityManager();
        final EntityTransaction entityTransaction = em.getTransaction();
        entityTransaction.begin();
        T result;
        try {
            result = transaction.execute(em);
            entityTransaction.commit();
        } catch (Exception e) {
            entityTransaction.rollback();
            throw e;
        }
        return result;
    }

    public void execute(final Transaction transaction) {
        final EntityManager em = factory.createEntityManager();
        final EntityTransaction entityTransaction = em.getTransaction();
        entityTransaction.begin();
        try {
            transaction.execute(em);
            entityTransaction.commit();
        } catch (Exception e) {
            LOGGER.error("An exception occurs during the transaction", e);
            entityTransaction.rollback();
        }
    }

}
