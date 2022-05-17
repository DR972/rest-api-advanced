package com.epam.esm.dao;

import com.epam.esm.entity.Customer;

/**
 * The interface {@code CustomerDao} describes abstract behavior for working with {@link com.epam.esm.dao.impl.CustomerDaoImpl} in database.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
public interface CustomerDao extends Dao<Customer, Long> {
}
