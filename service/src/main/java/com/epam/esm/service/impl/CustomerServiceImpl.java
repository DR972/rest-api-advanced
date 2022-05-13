package com.epam.esm.service.impl;

import com.epam.esm.dao.CustomerDao;
import com.epam.esm.dao.CustomerOrderDao;
import com.epam.esm.dto.CustomerOrderDto;
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
import org.springframework.util.LinkedMultiValueMap;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerMapper customerMapper;
    private final CustomerDao customerDao;
    private final CustomerOrderMapper customerOrderMapper;
    private final CustomerOrderDao customerOrderDao;

    @Autowired
    public CustomerServiceImpl(CustomerMapper customerMapper, CustomerDao customerDao, CustomerOrderMapper customerOrderMapper, CustomerOrderDao customerOrderDao) {
        this.customerMapper = customerMapper;
        this.customerDao = customerDao;
        this.customerOrderMapper = customerOrderMapper;
        this.customerOrderDao = customerOrderDao;
    }

    @Override
    public CustomerDto findCustomerById(long id) {
        return customerMapper.convertToDto(customerDao.findEntityById(id).orElseThrow(() ->
                new NoSuchEntityException("ex.noSuchEntity", " (id = " + id + ")")));
    }

    @Override
    public Customer findCustomerByName(String name) {
        return customerDao.findEntityByName(name).orElse(new Customer());
    }

    @Override
    public List<CustomerDto> findListCustomers(int pageNumber, int rows) {
        return customerDao.findListEntities(new LinkedMultiValueMap<>(), pageNumber, rows).stream().map(customerMapper::convertToDto).collect(Collectors.toList());
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
    public CustomerOrderDto findCustomerOrderByCustomerIdAndOrderId(long customerId, long orderId) {
        return customerOrderMapper.convertToDto(customerOrderDao.findCustomerOrder(customerId, orderId).orElseThrow(() ->
                new NoSuchEntityException("ex.noSuchEntity", " (customerId = " + customerId + ", orderId = " + orderId + ")")));
    }

    @Override
    public List<CustomerOrderDto> findAllCustomerOrdersByCustomerId(long customerId, int pageNumber, int rows) {
        findCustomerById(customerId);
        return customerOrderDao.findCustomerOrderList(customerId, pageNumber, rows).stream().map(customerOrderMapper::convertToDto).collect(Collectors.toList());
    }
}
