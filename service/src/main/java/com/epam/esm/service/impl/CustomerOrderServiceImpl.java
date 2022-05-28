package com.epam.esm.service.impl;

import com.epam.esm.dao.CustomerOrderDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.ResourceDto;
import com.epam.esm.dto.mapper.CustomerOrderMapper;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.service.CustomerOrderService;
import com.epam.esm.dto.CustomerOrderDto;
import com.epam.esm.service.CustomerService;
import com.epam.esm.service.DateHandler;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The class {@code CustomerOrderServiceImpl} is implementation of interface {@link CustomerOrderService}
 * and provides logic to work with {@link com.epam.esm.entity.CustomerOrder}.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@Service
public class CustomerOrderServiceImpl implements CustomerOrderService {
    /**
     * CustomerOrderDao customerOrderDao.
     */
    private final CustomerOrderDao customerOrderDao;
    /**
     * CustomerOrderMapper customerOrderMapper.
     */
    private final CustomerOrderMapper customerOrderMapper;
    /**
     * DateHandler dateHandler.
     */
    private final DateHandler dateHandler;
    /**
     * GiftCertificateService certificateService.
     */
    private final GiftCertificateService certificateService;
    /**
     * CustomerService customerService.
     */
    private final CustomerService customerService;

    /**
     * The constructor creates a CustomerOrderServiceImpl object
     *
     * @param customerOrderDao    CustomerOrderDao customerOrderDao
     * @param customerOrderMapper CustomerOrderMapper customerOrderMapper
     * @param dateHandler         DateHandler dateHandler
     * @param certificateService  GiftCertificateService certificateService
     * @param customerService     CustomerService customerService
     */
    @Autowired
    public CustomerOrderServiceImpl(CustomerOrderDao customerOrderDao, CustomerOrderMapper customerOrderMapper, DateHandler dateHandler,
                                    GiftCertificateService certificateService, CustomerService customerService) {
        this.customerOrderDao = customerOrderDao;
        this.customerOrderMapper = customerOrderMapper;
        this.dateHandler = dateHandler;
        this.certificateService = certificateService;
        this.customerService = customerService;
    }

    @Override
    public CustomerOrderDto findCustomerOrderById(String id) {
        return customerOrderMapper.convertToDto(customerOrderDao.findEntityById(Long.parseLong(id)).orElseThrow(() ->
                new NoSuchEntityException("ex.noSuchEntity", "id = " + id)));
    }

    @Override
    @Transactional
    public ResourceDto<CustomerOrderDto> findListCustomerOrders(int pageNumber, int rows) {
        List<CustomerOrderDto> customerOrders = customerOrderDao.findListEntities((pageNumber - 1) * rows, rows)
                .stream().map(customerOrderMapper::convertToDto).collect(Collectors.toList());
        return new ResourceDto<>(customerOrders, pageNumber, customerOrders.size(), customerOrderDao.countNumberEntityRows());
    }

    @Override
    @Transactional
    public CustomerOrderDto createCustomerOrder(String customerId, CustomerOrderDto customerOrderDto) {
        customerService.findCustomerById(customerId);
        customerOrderDto.setCustomerId(customerId);
        customerOrderDto.setPurchaseTime(dateHandler.getCurrentDate());
        List<GiftCertificateDto> certificateDtos = customerOrderDto.getGiftCertificates().stream().map(c -> certificateService.findCertificateById(c.getCertificateId()))
                .distinct().collect(Collectors.toList());
        BigDecimal amount = certificateDtos.stream().map(c -> new BigDecimal(c.getPrice().replace(',', '.'))).reduce(BigDecimal::add).orElse(new BigDecimal(0));
        customerOrderDto.setGiftCertificates(certificateDtos);
        customerOrderDto.setAmount(amount);
        return customerOrderMapper.convertToDto(customerOrderDao.createEntity(customerOrderMapper.convertToEntity(customerOrderDto)));
    }
}
