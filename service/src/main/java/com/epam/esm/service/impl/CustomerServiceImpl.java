package com.epam.esm.service.impl;

import com.epam.esm.dao.CustomerOrderDao;
import com.epam.esm.dao.impl.AbstractDao;
import com.epam.esm.dto.CustomerOrderDto;
import com.epam.esm.dto.ResourceDto;
import com.epam.esm.dto.mapper.CustomerOrderMapper;
import com.epam.esm.dto.mapper.EntityMapper;
import com.epam.esm.entity.Customer;
import com.epam.esm.exception.DuplicateEntityException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.service.CustomerService;
import com.epam.esm.dto.CustomerDto;
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
public class CustomerServiceImpl extends AbstractService<Customer, Long, CustomerDto> implements CustomerService {
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
     * @param dao                 AbstractDao<Tag, Long> dao
     * @param entityMapper        EntityMapper<Tag, TagDto> entityMapper
     * @param customerOrderMapper CustomerOrderMapper customerOrderMapper
     * @param customerOrderDao    CustomerOrderDao customerOrderDao
     */
    public CustomerServiceImpl(AbstractDao<Customer, Long> dao, EntityMapper<Customer, CustomerDto> entityMapper,
                               CustomerOrderMapper customerOrderMapper, CustomerOrderDao customerOrderDao) {
        super(dao, entityMapper);
        this.customerOrderMapper = customerOrderMapper;
        this.customerOrderDao = customerOrderDao;
    }

    @Override
    public Customer findCustomerByName(String name) {
        return dao.findEntityByName(name).orElse(new Customer());
    }

    @Override
    @Transactional
    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = entityMapper.convertToEntity(customerDto);
        if (findCustomerByName(customer.getName()).getName() != null) {
            throw new DuplicateEntityException("ex.duplicate", customer.getName());
        }
        return entityMapper.convertToDto(dao.createEntity(customer));
    }

    @Override
    public CustomerOrderDto findCustomerOrderByCustomerIdAndOrderId(String customerId, String orderId) {
        return customerOrderMapper.convertToDto(customerOrderDao.findCustomerOrder(Long.parseLong(customerId), Long.parseLong(orderId)).orElseThrow(() ->
                new NoSuchEntityException("ex.noSuchEntity", "customerId = " + customerId + ", orderId = " + orderId)));
    }

    @Override
    @Transactional
    public ResourceDto<CustomerOrderDto> findListCustomerOrdersByCustomerId(String customerId, int pageNumber, int rows) {
        findEntityById(customerId);
        List<CustomerOrderDto> customerOrders = customerOrderDao.findCustomerOrderList(Long.parseLong(customerId), (pageNumber - 1) * rows, rows)
                .stream().map(customerOrderMapper::convertToDto).collect(Collectors.toList());
        return new ResourceDto<>(customerOrders, pageNumber, customerOrders.size(), customerOrderDao
                .countNumberEntityRowsInListCustomerOrders(Long.parseLong(customerId)));
    }
}
