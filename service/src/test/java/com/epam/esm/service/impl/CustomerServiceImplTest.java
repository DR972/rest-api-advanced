package com.epam.esm.service.impl;

import com.epam.esm.dao.CustomerDao;
import com.epam.esm.dao.CustomerOrderDao;
import com.epam.esm.dao.impl.AbstractDao;
import com.epam.esm.dto.CustomerDto;
import com.epam.esm.dto.CustomerOrderDto;
import com.epam.esm.dto.ResourceDto;
import com.epam.esm.dto.mapper.CustomerMapper;
import com.epam.esm.dto.mapper.CustomerOrderMapper;
import com.epam.esm.entity.Customer;
import com.epam.esm.entity.CustomerOrder;
import com.epam.esm.exception.DuplicateEntityException;
import com.epam.esm.exception.NoSuchEntityException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest(classes = CustomerServiceImpl.class)
public class CustomerServiceImplTest {
    private static final String CUSTOMER_NAME_1 = "Customer1";
    private static final String NEW = "new";
    private static final CustomerOrder CUSTOMER_ORDER_1 = new CustomerOrder(1L, LocalDateTime.parse("2022-05-01T00:00:00.001"), new ArrayList<>(), new BigDecimal("80"));
    private static final CustomerOrder CUSTOMER_ORDER_2 = new CustomerOrder(2L, LocalDateTime.parse("2022-05-01T00:00:00.001"), new ArrayList<>(), new BigDecimal("180"));
    private static final CustomerOrder CUSTOMER_ORDER_3 = new CustomerOrder(3L, LocalDateTime.parse("2022-05-01T00:00:00.001"), new ArrayList<>(), new BigDecimal("100"));
    private static final CustomerOrder CUSTOMER_ORDER_5 = new CustomerOrder(5L, LocalDateTime.parse("2022-05-01T00:00:00.001"), new ArrayList<>(), new BigDecimal("120"));
    private static final CustomerOrder CUSTOMER_ORDER_6 = new CustomerOrder(6L, LocalDateTime.parse("2022-05-01T00:00:00.001"), new ArrayList<>(), new BigDecimal("60"));
    private static final CustomerOrder CUSTOMER_ORDER_7 = new CustomerOrder(7L, LocalDateTime.parse("2022-05-01T00:00:00.001"), new ArrayList<>(), new BigDecimal("40"));

    private static final Customer CUSTOMER_1 = new Customer(1, "Customer1", Arrays.asList(CUSTOMER_ORDER_1, CUSTOMER_ORDER_5));
    private static final Customer CUSTOMER_2 = new Customer(2, "Customer2", Arrays.asList(CUSTOMER_ORDER_2, CUSTOMER_ORDER_6));
    private static final Customer CUSTOMER_3 = new Customer(3, "Customer3", Arrays.asList(CUSTOMER_ORDER_3, CUSTOMER_ORDER_7));
    private static final Customer NEW_CUSTOMER = new Customer(0, NEW, new ArrayList<>());

    private static final CustomerOrderDto CUSTOMER_ORDER_DTO_1 = new CustomerOrderDto("1", "1", LocalDateTime.parse("2022-05-01T00:00:00.001"), new ArrayList<>(), new BigDecimal("80"));
    private static final CustomerOrderDto CUSTOMER_ORDER_DTO_2 = new CustomerOrderDto("2", "2", LocalDateTime.parse("2022-05-01T00:00:00.001"), new ArrayList<>(), new BigDecimal("180"));
    private static final CustomerOrderDto CUSTOMER_ORDER_DTO_3 = new CustomerOrderDto("3", "3", LocalDateTime.parse("2022-05-01T00:00:00.001"), new ArrayList<>(), new BigDecimal("100"));
    private static final CustomerOrderDto CUSTOMER_ORDER_DTO_5 = new CustomerOrderDto("5", "1", LocalDateTime.parse("2022-05-01T00:00:00.001"), new ArrayList<>(), new BigDecimal("120"));
    private static final CustomerOrderDto CUSTOMER_ORDER_DTO_6 = new CustomerOrderDto("6", "2", LocalDateTime.parse("2022-05-01T00:00:00.001"), new ArrayList<>(), new BigDecimal("60"));
    private static final CustomerOrderDto CUSTOMER_ORDER_DTO_7 = new CustomerOrderDto("7", "3", LocalDateTime.parse("2022-05-01T00:00:00.001"), new ArrayList<>(), new BigDecimal("40"));

    private static final CustomerDto CUSTOMER_DTO_1 = new CustomerDto("1", "Customer1", Arrays.asList(CUSTOMER_ORDER_DTO_1, CUSTOMER_ORDER_DTO_5));
    private static final CustomerDto CUSTOMER_DTO_2 = new CustomerDto("2", "Customer2", Arrays.asList(CUSTOMER_ORDER_DTO_2, CUSTOMER_ORDER_DTO_6));
    private static final CustomerDto CUSTOMER_DTO_3 = new CustomerDto("3", "Customer3", Arrays.asList(CUSTOMER_ORDER_DTO_3, CUSTOMER_ORDER_DTO_7));
    private static final CustomerDto NEW_DTO_CUSTOMER = new CustomerDto("0", NEW, new ArrayList<>());

    @MockBean
    private AbstractDao<Customer, Long> dao;
    @MockBean
    private CustomerDao customerDao;
    @MockBean
    private CustomerMapper customerMapper;
    @MockBean
    private CustomerOrderDao customerOrderDao;
    @MockBean
    private CustomerOrderMapper customerOrderMapper;
    @Autowired
    private CustomerServiceImpl customerService;

    @Test
    void findCustomerByIdShouldReturnResult() {
        when(customerMapper.convertToDto(CUSTOMER_2)).thenReturn(CUSTOMER_DTO_2);
        when(dao.findEntityById(2L)).thenReturn(Optional.of(CUSTOMER_2));
        customerService.findEntityById("2");
        verify(dao, times(1)).findEntityById(2L);
        assertEquals(CUSTOMER_DTO_2, customerService.findEntityById("2"));
    }

    @Test
    void findCustomerByIdShouldThrowException() {
        when(dao.findEntityById(2L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(NoSuchEntityException.class, () -> customerService.findEntityById("2"));
        verify(dao, times(1)).findEntityById(2L);
        assertTrue(exception.getMessage().contains("ex.noSuchEntity"));
    }

    @Test
    void findCustomerByNameShouldReturnResult() {
        when(dao.findEntityByName(CUSTOMER_NAME_1)).thenReturn(Optional.of(CUSTOMER_1));
        customerService.findCustomerByName(CUSTOMER_NAME_1);
        verify(dao, times(1)).findEntityByName(CUSTOMER_NAME_1);
        assertEquals(CUSTOMER_1, customerService.findCustomerByName(CUSTOMER_NAME_1));
    }

    @Test
    void findListCustomersShouldReturnResult() {
        when(customerMapper.convertToDto(CUSTOMER_1)).thenReturn(CUSTOMER_DTO_1);
        when(customerMapper.convertToDto(CUSTOMER_2)).thenReturn(CUSTOMER_DTO_2);
        when(customerMapper.convertToDto(CUSTOMER_3)).thenReturn(CUSTOMER_DTO_3);
        when(dao.countNumberEntityRows()).thenReturn(5L);
        when(dao.findListEntities(0, 5)).thenReturn(Arrays.asList(CUSTOMER_1, CUSTOMER_2, CUSTOMER_3));
        customerService.findListEntities(1, 5);
        verify(dao, times(1)).findListEntities(0, 5);
        assertEquals(new ResourceDto<>(Arrays.asList(CUSTOMER_DTO_1, CUSTOMER_DTO_2, CUSTOMER_DTO_3), 1, 3, 5),
                customerService.findListEntities(1, 5));
    }

    @Test
    void createCustomerShouldReturnResult() {
        when(customerMapper.convertToEntity(NEW_DTO_CUSTOMER)).thenReturn(NEW_CUSTOMER);
        when(customerMapper.convertToDto(NEW_CUSTOMER)).thenReturn(NEW_DTO_CUSTOMER);
        when(dao.findEntityByName(NEW)).thenReturn(Optional.of(new Customer()));
        when(dao.createEntity(NEW_CUSTOMER)).thenReturn(NEW_CUSTOMER);
        customerService.createCustomer(NEW_DTO_CUSTOMER);
        verify(dao, times(1)).createEntity(NEW_CUSTOMER);
        assertEquals(NEW_DTO_CUSTOMER, customerService.createCustomer(NEW_DTO_CUSTOMER));
    }

    @Test
    void createCustomerShouldThrowException() {
        when(customerMapper.convertToEntity(CUSTOMER_DTO_1)).thenReturn(CUSTOMER_1);
        when(dao.findEntityByName(CUSTOMER_NAME_1)).thenReturn(Optional.of(CUSTOMER_1));
        Exception exception = assertThrows(DuplicateEntityException.class, () -> customerService.createCustomer(CUSTOMER_DTO_1));
        verify(dao, times(1)).findEntityByName(CUSTOMER_NAME_1);
        assertTrue(exception.getMessage().contains("ex.duplicate"));
    }

    @Test
    void findCustomerOrderByCustomerIdAndOrderIdShouldReturnResult() {
        when(customerOrderMapper.convertToDto(CUSTOMER_ORDER_2)).thenReturn(CUSTOMER_ORDER_DTO_2);
        when(customerOrderDao.findCustomerOrder(2, 2)).thenReturn(Optional.of(CUSTOMER_ORDER_2));
        customerService.findCustomerOrderByCustomerIdAndOrderId("2", "2");
        verify(customerOrderDao, times(1)).findCustomerOrder(2, 2);
        assertEquals(CUSTOMER_ORDER_DTO_2, customerService.findCustomerOrderByCustomerIdAndOrderId("2", "2"));
    }

    @Test
    void findCustomerOrderByCustomerIdAndOrderIdShouldThrowException() {
        when(customerOrderDao.findCustomerOrder(2, 2)).thenReturn(Optional.empty());
        Exception exception = assertThrows(NoSuchEntityException.class, () -> customerService.findCustomerOrderByCustomerIdAndOrderId("2", "2"));
        verify(customerOrderDao, times(1)).findCustomerOrder(2, 2);
        assertTrue(exception.getMessage().contains("ex.noSuchEntity"));
    }

    @Test
    void findAllCustomerOrdersByCustomerIdShouldReturnResult() {
        when(customerOrderMapper.convertToDto(CUSTOMER_ORDER_3)).thenReturn(CUSTOMER_ORDER_DTO_3);
        when(customerOrderMapper.convertToDto(CUSTOMER_ORDER_7)).thenReturn(CUSTOMER_ORDER_DTO_7);
        when(customerMapper.convertToDto(CUSTOMER_3)).thenReturn(CUSTOMER_DTO_3);
        when(customerOrderDao.countNumberEntityRowsInListCustomerOrders(3)).thenReturn(2L);
        when(dao.findEntityById(3L)).thenReturn(Optional.of(CUSTOMER_3));
        when(customerOrderDao.findCustomerOrderList(3, 0, 5)).thenReturn(Arrays.asList(CUSTOMER_ORDER_3, CUSTOMER_ORDER_7));
        customerService.findListCustomerOrdersByCustomerId("3", 1, 5);
        verify(customerOrderDao, times(1)).findCustomerOrderList(3, 0, 5);
        assertEquals(new ResourceDto<>(Arrays.asList(CUSTOMER_ORDER_DTO_3, CUSTOMER_ORDER_DTO_7), 1, 2, 2),
                customerService.findListCustomerOrdersByCustomerId("3", 1, 5));
    }

    @Test
    void findAllCustomerOrdersByCustomerIdShouldThrowException() {
        when(customerMapper.convertToEntity(CUSTOMER_DTO_1)).thenReturn(CUSTOMER_1);
        when(dao.findEntityByName(CUSTOMER_NAME_1)).thenReturn(Optional.of(CUSTOMER_1));
        Exception exception = assertThrows(DuplicateEntityException.class, () -> customerService.createCustomer(CUSTOMER_DTO_1));
        verify(dao, times(1)).findEntityByName(CUSTOMER_NAME_1);
        assertTrue(exception.getMessage().contains("ex.duplicate"));
    }
}
