package com.epam.esm.dao;

import com.epam.esm.entity.BaseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;

/**
 * The interface {@code Dao} describes abstract behavior for working with
 * {@link com.epam.esm.dao.impl.AbstractDao} in database.
 *
 * @param <T> indicates that for this instantiation of the DAO, will be used this type of Entity implementation.
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@Repository
public interface Dao<T extends BaseEntity<ID>, ID> {

    /**
     * The method finds objects T in the table 'T' by id.
     *
     * @param id long id
     * @return T object
     */
    Optional<T> findEntityById(long id);

    /**
     * The method finds objects T in the table 'T' by name.
     *
     * @param name String name
     * @return T object
     */
    Optional<T> findEntityByName(String name);

    /**
     * The method finds list T objects in the table `T`.
     *
     * @param params     MultiValueMap<String, String> params
     * @param pageNumber int pageNumber
     * @param rows       int rows
     * @return list of T objects
     */
    List<T> findListEntities(MultiValueMap<String, String> params, int pageNumber, int rows);

    /**
     * The method performs the operation of saving the object T in the table 'T'.
     *
     * @param t T t
     * @return T object from the table 'T'
     */
    T createEntity(T t);

    /**
     * The method performs operation of updating on the object T in the table 'T'.
     *
     * @param t T t
     * @return T object from the table 'T'
     */
    T updateEntity(T t);

    /**
     * The method performs operation of deleting on the object T in the table 'T'.
     *
     * @param t T t
     */
    void deleteEntity(T t);

    /**
     * The method finds count number of rows objects T in the table 'T'.
     *
     * @param params MultiValueMap<String, String> params
     * @return count number of rows objects
     */
    long countNumberEntityRows(MultiValueMap<String, String> params);
}
