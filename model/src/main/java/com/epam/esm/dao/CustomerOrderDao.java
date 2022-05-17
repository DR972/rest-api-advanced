package com.epam.esm.dao;

import com.epam.esm.entity.CustomerOrder;

import java.util.List;
import java.util.Optional;

/**
 * The interface {@code CustomerOrderDao} describes abstract behavior for working with {@link com.epam.esm.dao.impl.CustomerOrderDaoImpl} in database.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
public interface CustomerOrderDao extends Dao<CustomerOrder, Long> {
    Optional<CustomerOrder> findCustomerOrder(long customerId, long orderId);

    /**
     * The method finds list CustomerOrder objects in the table `CustomerOrder`.
     *
     * @param customerId long customer Id
     * @param pageNumber int pageNumber
     * @param rows       int rows
     * @return list of CustomerOrder objects
     */
    List<CustomerOrder> findCustomerOrderList(long customerId, int pageNumber, int rows);
}
