package com.epam.esm.service;

import com.epam.esm.dto.CustomerDto;
import com.epam.esm.dto.CustomerOrderDto;
import com.epam.esm.dto.ListEntitiesDto;
import com.epam.esm.entity.Customer;

/**
 * The interface {@code CustomerService} describes abstract behavior for working with {@link com.epam.esm.service.impl.CustomerServiceImpl} objects.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
public interface CustomerService {
    /**
     * The method finds Customer by id.
     *
     * @param id CustomerDto id
     * @return CustomerDto object
     */
    CustomerDto findCustomerById(long id);

    /**
     * The method finds Customer by name.
     *
     * @param name Customer name
     * @return Customer object
     */
    Customer findCustomerByName(String name);

    /**
     * The method finds list Customers.
     *
     * @param pageNumber int pageNumber
     * @param rows       int rows
     * @return list of CustomerDto objects
     */
    ListEntitiesDto<CustomerDto> findListCustomers(int pageNumber, int rows);

    /**
     * The method performs the operation of saving Customer.
     *
     * @param customerDto CustomerDto
     * @return CustomerDto object
     */
    CustomerDto createCustomer(CustomerDto customerDto);

    /**
     * The method finds Customer by Customer id and Order id.
     *
     * @param customerId long customerId
     * @param orderId    long orderId
     * @return CustomerDto object
     */
    CustomerOrderDto findCustomerOrderByCustomerIdAndOrderId(long customerId, long orderId);

    /**
     * The method finds list Customer orders by Customer id.
     *
     * @param customerId long customerId
     * @param pageNumber int pageNumber
     * @param rows       int rows
     * @return ListEntitiesDto<CustomerOrderDto>
     */
    ListEntitiesDto<CustomerOrderDto> findListCustomerOrdersByCustomerId(long customerId, int pageNumber, int rows);
}
