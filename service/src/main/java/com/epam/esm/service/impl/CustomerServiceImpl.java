package com.epam.esm.service.impl;

import com.epam.esm.dao.CustomerDao;
import com.epam.esm.dao.CustomerOrderDao;
import com.epam.esm.dto.CustomerOrderDto;
import com.epam.esm.dto.ListEntitiesDto;
import com.epam.esm.dto.mapper.CustomerMapper;
import com.epam.esm.dto.mapper.CustomerOrderMapper;
import com.epam.esm.entity.Customer;
import com.epam.esm.exception.DuplicateEntityException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.service.CustomerService;
import com.epam.esm.dto.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The class {@code CustomerServiceImpl} is implementation of interface {@link CustomerService}
 * and provides logic to work with {@link com.epam.esm.entity.Customer}.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    /**
     * CustomerMapper customerMapper.
     */
    private final CustomerMapper customerMapper;
    /**
     * CustomerDao customerDao.
     */
    private final CustomerDao customerDao;
    /**
     * CustomerOrderMapper customerOrderMapper.
     */
    private final CustomerOrderMapper customerOrderMapper;
    /**
     * CustomerOrderDao customerOrderDao.
     */
    private final CustomerOrderDao customerOrderDao;

    /**
     * The constructor creates a CustomerServiceImpl object
     *
     * @param customerMapper      CustomerMapper customerMapper
     * @param customerDao         CustomerDao customerDao
     * @param customerOrderMapper CustomerOrderMapper customerOrderMapper
     * @param customerOrderDao    CustomerOrderDao customerOrderDao
     */
    @Autowired
    public CustomerServiceImpl(CustomerMapper customerMapper, CustomerDao customerDao, CustomerOrderMapper customerOrderMapper, CustomerOrderDao customerOrderDao) {
        this.customerMapper = customerMapper;
        this.customerDao = customerDao;
        this.customerOrderMapper = customerOrderMapper;
        this.customerOrderDao = customerOrderDao;
    }

    @Override
    public CustomerDto findCustomerById(String id) {
        return customerMapper.convertToDto(customerDao.findEntityById(Long.parseLong(id)).orElseThrow(() ->
                new NoSuchEntityException("ex.noSuchEntity", " (id = " + id + ")")));
    }

    @Override
    public Customer findCustomerByName(String name) {
        return customerDao.findEntityByName(name).orElse(new Customer());
    }

    @Override
    @Transactional
    public ListEntitiesDto<CustomerDto> findListCustomers(int pageNumber, int rows) {
        List<CustomerDto> customerDtos = customerDao.findListEntities((pageNumber - 1) * rows, rows)
                .stream().map(customerMapper::convertToDto).collect(Collectors.toList());
        return new ListEntitiesDto<>(customerDtos, pageNumber, customerDtos.size(), customerDao.countNumberEntityRows());
    }

    @Override
    @Transactional
    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = customerMapper.convertToEntity(customerDto);
        if (findCustomerByName(customer.getName()).getName() != null) {
            throw new DuplicateEntityException("ex.duplicate", customer.getName() + ")");
        }
        return customerMapper.convertToDto(customerDao.createEntity(customer));
    }

    @Override
    public CustomerOrderDto findCustomerOrderByCustomerIdAndOrderId(String customerId, String orderId) {
        return customerOrderMapper.convertToDto(customerOrderDao.findCustomerOrder(Long.parseLong(customerId), Long.parseLong(orderId)).orElseThrow(() ->
                new NoSuchEntityException("ex.noSuchEntity", " (customerId = " + customerId + ", orderId = " + orderId + ")")));
    }

    @Override
    @Transactional
    public ListEntitiesDto<CustomerOrderDto> findListCustomerOrdersByCustomerId(String customerId, int pageNumber, int rows) {
        findCustomerById(customerId);
        List<CustomerOrderDto> customerOrders = customerOrderDao.findCustomerOrderList(Long.parseLong(customerId), (pageNumber - 1) * rows, rows)
                .stream().map(customerOrderMapper::convertToDto).collect(Collectors.toList());
        return new ListEntitiesDto<>(customerOrders, pageNumber, customerOrders.size(), customerOrderDao
                .countNumberEntityRowsInListCustomerOrders(Long.parseLong(customerId)));
    }
}
