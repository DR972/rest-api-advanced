package com.epam.esm.service;

import com.epam.esm.dto.CustomerOrderDto;

import java.util.List;

public interface CustomerOrderService {
    /**
     * The method finds customerOrder.
     *
     * @param id customerOrderDto id
     * @return customerOrderDto object
     */
    CustomerOrderDto findCustomerOrderById(long id);

    /**
     * The method finds all customerOrders.
     * <p>
     * //     * @param params      MultiValueMap<String, String> all request params
     * //     * @param queryParams array of parameters for the query
     *
     * @return list of customerOrderDto objects
     */
    List<CustomerOrderDto> findAllCustomerOrders(int pageNumber, int rows);

    /**
     * The method performs the operation of saving CustomerOrder.
     *
     * @param customerOrderDto CustomerOrderDto
     * @return CustomerOrderDto object
     */
    CustomerOrderDto createCustomerOrder(long customerId, CustomerOrderDto customerOrderDto);
}
