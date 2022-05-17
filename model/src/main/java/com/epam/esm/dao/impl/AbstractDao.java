package com.epam.esm.dao.impl;

import com.epam.esm.dao.Dao;
import com.epam.esm.entity.BaseEntity;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;

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
     * JdbcTemplate jdbcTemplate.
     */
    @PersistenceContext
    protected EntityManager entityManager;
    protected final Class<T> entityType;

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
    public List<T> findListEntities(MultiValueMap<String, String> params, int pageNumber, int rows) {
        return entityManager.unwrap(Session.class).createQuery("from " + entityType.getSimpleName())
                .setFirstResult(pageNumber).setMaxResults(rows).getResultList();
    }

    @Override
    public T createEntity(T t) {
        entityManager.unwrap(Session.class).save(t);
        return t;
    }

    @Override
    public T updateEntity(T t) {
        entityManager.unwrap(Session.class).merge(t);
        return t;
    }

    @Override
    public void deleteEntity(T t) {
        entityManager.unwrap(Session.class).delete(entityManager.merge(t));
    }
}
