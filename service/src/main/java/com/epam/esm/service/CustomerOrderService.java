package com.epam.esm.service;

import com.epam.esm.dto.CustomerOrderDto;
import com.epam.esm.dto.ListEntitiesDto;

/**
 * The interface {@code CustomerOrderService} describes abstract behavior for working with
 * {@link com.epam.esm.service.impl.CustomerOrderServiceImpl} objects.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
public interface CustomerOrderService {
    /**
     * The method finds customerOrder.
     *
     * @param id customerOrderDto id
     * @return customerOrderDto object
     */
    CustomerOrderDto findCustomerOrderById(long id);

    /**
     * The method finds list customerOrders.
     *
     * @param pageNumber int pageNumber
     * @param rows       int rows
     * @return ListEntitiesDto<CustomerOrderDto>
     */
    ListEntitiesDto<CustomerOrderDto> findListCustomerOrders(int pageNumber, int rows);

    /**
     * The method performs the operation of saving CustomerOrder.
     *
     * @param customerId       long customerId
     * @param customerOrderDto CustomerOrderDto
     * @return CustomerOrderDto object
     */
    CustomerOrderDto createCustomerOrder(long customerId, CustomerOrderDto customerOrderDto);
}
