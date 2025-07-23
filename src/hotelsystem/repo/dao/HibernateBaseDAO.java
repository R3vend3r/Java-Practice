package hotelsystem.repo.dao;

import hotelsystem.Exception.DatabaseException;
import hotelsystem.Utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;


import java.util.List;

public abstract class HibernateBaseDAO<T, ID> implements GenericDAO<T, ID> {
    protected Class<T> entityClass;

    protected HibernateBaseDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public void create(T entity) throws DatabaseException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtils.getSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                try {
                    transaction.rollback();
                } catch (Exception rollbackEx) {
                    System.err.println("Failed to rollback transaction: " + rollbackEx.getMessage());
                }
            }
            throw new DatabaseException("Failed to create entity", e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public T findById(ID id) throws DatabaseException {
        try (Session session = HibernateUtils.getSession()) {
            return session.get(entityClass, id);
        } catch (Exception e) {
            throw new DatabaseException("Failed to find entity by id: " + id, e);
        }
    }

    @Override
    public List<T> findAll() throws DatabaseException {
        try (Session session = HibernateUtils.getSession()) {
            return session.createQuery("FROM " + entityClass.getSimpleName(), entityClass).list();
        } catch (Exception e) {
            throw new DatabaseException("Failed to find all entities", e);
        }
    }

    @Override
    public void update(T entity) throws DatabaseException {
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSession()) {
            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException("Failed to update entity", e);
        }
    }

    @Override
    public void delete(ID id) throws DatabaseException {
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSession()) {
            transaction = session.beginTransaction();
            T entity = session.get(entityClass, id);
            if (entity != null) {
                session.remove(entity);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException("Failed to delete entity with id: " + id, e);
        }
    }
}