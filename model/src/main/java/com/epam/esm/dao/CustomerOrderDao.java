package com.epam.esm.dao;

import com.epam.esm.entity.CustomerOrder;

import java.util.List;
import java.util.Optional;

public interface CustomerOrderDao extends Dao<CustomerOrder, Long> {
    Optional<CustomerOrder> findCustomerOrder(long customerId, long orderId);

    /**
     * The method finds all T objects in the table `T`.
     * <p>
     * //     * @param query  database query
     * //     * @param params array of parameters for the query
     *
     * @return list of T objects
     */
    List<CustomerOrder> findCustomerOrderList(long customerId, int pageNumber, int rows);
}
