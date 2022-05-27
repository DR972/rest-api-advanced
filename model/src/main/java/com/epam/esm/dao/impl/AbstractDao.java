package com.epam.esm.dao.impl;

import com.epam.esm.dao.Dao;
import com.epam.esm.entity.BaseEntity;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * The class {@code AbstractDao} is designed for basic work with database tables.
 *
 * @param <T> indicates that for this instantiation of the DAO, will be used this type of Entity implementation.
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@Repository

public abstract class AbstractDao<T extends BaseEntity<ID>, ID> implements Dao<T, ID> {
    /**
     * EntityManager entityManager.
     */
    @PersistenceContext
    protected EntityManager entityManager;
    /**
     * Class<T> entityType.
     */
    protected final Class<T> entityType;

    /**
     * The constructor creates an AbstractDao object
     *
     * @param entityType Class<T>
     */
    @Autowired
    protected AbstractDao(Class<T> entityType) {
        this.entityType = entityType;
    }

    @Override
    public Optional<T> findEntityById(long id) {
        return entityManager.unwrap(Session.class).createQuery("SELECT e FROM " + entityType.getSimpleName() + " e WHERE e.id = :id", entityType)
                .setParameter("id", id)
                .getResultList().stream()
                .findFirst();
    }

    @Override
    public Optional<T> findEntityByName(String name) {
        return entityManager.unwrap(Session.class).createQuery("SELECT e FROM " + entityType.getSimpleName() + " e WHERE e.name = :name", entityType)
                .setParameter("name", name)
                .getResultList().stream()
                .findFirst();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findListEntities(int offset, int limit) {
        return entityManager.unwrap(Session.class).createQuery("from " + entityType.getSimpleName() + " order by id")
                .setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Override
    public T createEntity(T entity) {
        entityManager.unwrap(Session.class).save(entity);
        return entity;
    }

    @Override
    public T updateEntity(T entity) {
        entityManager.unwrap(Session.class).merge(entity);
        return entity;
    }

    @Override
    public void deleteEntity(T entity) {
        entityManager.unwrap(Session.class).delete(entityManager.merge(entity));
    }

    @Override
    public long countNumberEntityRows() {
        return entityManager.unwrap(Session.class).createQuery("from " + entityType.getSimpleName()).getResultList().size();
    }
}
