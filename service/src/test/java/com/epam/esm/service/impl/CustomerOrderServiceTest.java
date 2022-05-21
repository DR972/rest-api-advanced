package com.epam.esm.service.impl;

import com.epam.esm.dao.CustomerOrderDao;
import com.epam.esm.dto.*;
import com.epam.esm.dto.mapper.CustomerOrderMapper;
import com.epam.esm.entity.Customer;
import com.epam.esm.entity.CustomerOrder;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.service.CustomerService;
import com.epam.esm.service.DateHandler;
import com.epam.esm.service.GiftCertificateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class CustomerOrderServiceTest {
    private static final GiftCertificate GIFT_CERTIFICATE_1 = new GiftCertificate(1, "ATV riding",
            "Description ATV riding", new BigDecimal("100"), 10, LocalDateTime.parse("2022-04-01T10:12:45.123"),
            LocalDateTime.parse("2022-04-07T14:15:13.257"), Arrays.asList(new Tag(1, "rest"), new Tag(2, "nature"), new Tag(4, "atv")));

    private static final GiftCertificate GIFT_CERTIFICATE_2 = new GiftCertificate(2, "Horse riding",
            "Horse riding description", new BigDecimal("80"), 8, LocalDateTime.parse("2022-04-02T10:12:45.123"),
            LocalDateTime.parse("2022-04-05T14:15:13.257"), Arrays.asList(new Tag(1, "rest"), new Tag(2, "nature"), new Tag(5, "horse")));

    private static final GiftCertificateDto GIFT_CERTIFICATE_DTO_1 = new GiftCertificateDto("1", "ATV riding",
            "Description ATV riding", new BigDecimal("100"), 10, LocalDateTime.parse("2022-04-01T10:12:45.123"),
            LocalDateTime.parse("2022-04-07T14:15:13.257"), Arrays.asList(new TagDto("1", "rest"), new TagDto("2", "nature"), new TagDto("4", "atv")));

    private static final GiftCertificateDto GIFT_CERTIFICATE_DTO_2 = new GiftCertificateDto("2", "Horse riding",
            "Horse riding description", new BigDecimal("80"), 8, LocalDateTime.parse("2022-04-02T10:12:45.123"),
            LocalDateTime.parse("2022-04-05T14:15:13.257"), Arrays.asList(new TagDto("1", "rest"), new TagDto("2", "nature"), new TagDto("5", "horse")));


    private static final CustomerOrder CUSTOMER_ORDER_1 = new CustomerOrder(1L, LocalDateTime.parse("2022-05-01T00:00:00.001"), new ArrayList<>(), new BigDecimal("80"));
    private static final CustomerOrder CUSTOMER_ORDER_2 = new CustomerOrder(2L, LocalDateTime.parse("2022-05-01T00:00:00.001"), new ArrayList<>(), new BigDecimal("180"));
    private static final CustomerOrder CUSTOMER_ORDER_3 = new CustomerOrder(3L, LocalDateTime.parse("2022-05-01T00:00:00.001"), new ArrayList<>(), new BigDecimal("100"));
    private static final CustomerOrder NEW_CUSTOMER_ORDER = new CustomerOrder(0, new Customer(), LocalDateTime.parse("2022-05-01T00:00:00.001"),
            Arrays.asList(GIFT_CERTIFICATE_1, GIFT_CERTIFICATE_2), new BigDecimal("180"));

    private static final CustomerOrderDto CUSTOMER_ORDER_DTO_1 = new CustomerOrderDto("1", "1", LocalDateTime.parse("2022-05-01T00:00:00.001"), new ArrayList<>(), new BigDecimal("80"));
    private static final CustomerOrderDto CUSTOMER_ORDER_DTO_2 = new CustomerOrderDto("2", "2", LocalDateTime.parse("2022-05-01T00:00:00.001"), new ArrayList<>(), new BigDecimal("180"));
    private static final CustomerOrderDto CUSTOMER_ORDER_DTO_3 = new CustomerOrderDto("3", "3", LocalDateTime.parse("2022-05-01T00:00:00.001"), new ArrayList<>(), new BigDecimal("100"));
    private static final CustomerOrderDto CUSTOMER_ORDER_DTO_6 = new CustomerOrderDto("6", "2", LocalDateTime.parse("2022-05-01T00:00:00.001"), new ArrayList<>(), new BigDecimal("60"));
    private static final CustomerOrderDto NEW_DTO_CUSTOMER_ORDER = new CustomerOrderDto("0", "2", null, Arrays.asList(GIFT_CERTIFICATE_DTO_1, GIFT_CERTIFICATE_DTO_2), null);

    private static final CustomerDto CUSTOMER_DTO_2 = new CustomerDto("2", "Customer2", Arrays.asList(CUSTOMER_ORDER_DTO_2, CUSTOMER_ORDER_DTO_6));

    @Mock
    private CustomerOrderDao customerOrderDao = mock(CustomerOrderDao.class);
    @Mock
    private CustomerOrderMapper customerOrderMapper = mock(CustomerOrderMapper.class);
    @Mock
    private CustomerService customerService = mock(CustomerService.class);
    @Mock
    private GiftCertificateService certificateService = mock(GiftCertificateService.class);
    @Mock
    private DateHandler dateHandler = mock(DateHandler.class);
    @InjectMocks
    private CustomerOrderServiceImpl customerOrderService;

    @Test
    void findCustomerOrderByIdShouldReturnResult() {
        when(customerOrderMapper.convertToDto(CUSTOMER_ORDER_2)).thenReturn(CUSTOMER_ORDER_DTO_2);
        when(customerOrderDao.findEntityById(2L)).thenReturn(Optional.of(CUSTOMER_ORDER_2));
        customerOrderService.findCustomerOrderById("2");
        verify(customerOrderDao, times(1)).findEntityById(2L);
        assertEquals(CUSTOMER_ORDER_DTO_2, customerOrderService.findCustomerOrderById("2"));
    }

    @Test
    void findCustomerOrderByIddShouldThrowException() {
        when(customerOrderDao.findEntityById(2L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(NoSuchEntityException.class, () -> customerOrderService.findCustomerOrderById("2"));
        assertTrue(exception.getMessage().contains("ex.noSuchEntity"));
    }

    @Test
    void findAllCustomerOrdersShouldReturnResult() {
        when(customerOrderMapper.convertToDto(CUSTOMER_ORDER_1)).thenReturn(CUSTOMER_ORDER_DTO_1);
        when(customerOrderMapper.convertToDto(CUSTOMER_ORDER_2)).thenReturn(CUSTOMER_ORDER_DTO_2);
        when(customerOrderMapper.convertToDto(CUSTOMER_ORDER_3)).thenReturn(CUSTOMER_ORDER_DTO_3);
        when(customerOrderDao.countNumberEntityRows()).thenReturn(5L);
        when(customerOrderDao.findListEntities(0, 5)).thenReturn(Arrays.asList(CUSTOMER_ORDER_1, CUSTOMER_ORDER_2, CUSTOMER_ORDER_3));
        customerOrderService.findListCustomerOrders(1, 5);
        verify(customerOrderDao, times(1)).findListEntities(0, 5);
        assertEquals(new ListEntitiesDto<>(Arrays.asList(CUSTOMER_ORDER_DTO_1, CUSTOMER_ORDER_DTO_2, CUSTOMER_ORDER_DTO_3), 1, 3, 5),
                customerOrderService.findListCustomerOrders(1, 5));
    }

    @Test
    void createCustomerOrderShouldReturnResult() {
        when(customerService.findCustomerById("2")).thenReturn(CUSTOMER_DTO_2);
        when(certificateService.findCertificateById("1")).thenReturn(GIFT_CERTIFICATE_DTO_1);
        when(certificateService.findCertificateById("2")).thenReturn(GIFT_CERTIFICATE_DTO_2);

        when(customerOrderMapper.convertToEntity(NEW_DTO_CUSTOMER_ORDER)).thenReturn(NEW_CUSTOMER_ORDER);
        when(customerOrderMapper.convertToDto(NEW_CUSTOMER_ORDER)).thenReturn(NEW_DTO_CUSTOMER_ORDER);
        when(customerOrderDao.createEntity(NEW_CUSTOMER_ORDER)).thenReturn(NEW_CUSTOMER_ORDER);
        customerOrderService.createCustomerOrder("2", NEW_DTO_CUSTOMER_ORDER);
        verify(customerOrderDao, times(1)).createEntity(NEW_CUSTOMER_ORDER);
        assertEquals(NEW_DTO_CUSTOMER_ORDER, customerOrderService.createCustomerOrder("2", NEW_DTO_CUSTOMER_ORDER));
    }
}
