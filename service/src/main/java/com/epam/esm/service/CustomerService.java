package com.epam.esm.service;

import com.epam.esm.dto.CustomerDto;
import com.epam.esm.dto.CustomerOrderDto;
import com.epam.esm.entity.Customer;

import java.util.List;

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
     * The method finds all Customers.
     *
     * @return list of CustomerDto objects
     */
    List<CustomerDto> findListCustomers(int pageNumber, int rows);

    /**
     * The method performs the operation of saving Customer.
     *
     * @param customerDto CustomerDto
     * @return CustomerDto object
     */
    CustomerDto createCustomer(CustomerDto customerDto);

    /**
     * The method performs the operation of updating Customer.
     * <p>
     * //     * @param customerDto new CustomerDto parameters
     * //     * @param id          CustomerDto id
     *
     * @return CustomerDto object
     */
    CustomerOrderDto findCustomerOrderByCustomerIdAndOrderId(long customerId, long orderId);

    /**
     * The method performs the operation of updating Customer.
     * <p>
     * //     * @param customerDto new CustomerDto parameters
     * //     * @param id          CustomerDto id
     *
     * @return CustomerDto object
     */
    List<CustomerOrderDto> findAllCustomerOrdersByCustomerId(long customerId, int pageNumber, int rows);
}
