package com.epam.esm.service.impl;

import com.epam.esm.dao.CustomerOrderDao;
import com.epam.esm.dto.GiftCertificateDto;
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
import org.springframework.util.LinkedMultiValueMap;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerOrderServiceImpl implements CustomerOrderService {
    private final CustomerOrderDao customerOrderDao;
    private final CustomerOrderMapper customerOrderMapper;
    private final DateHandler dateHandler;
    private final GiftCertificateService certificateService;
    private final CustomerService customerService;

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
    public CustomerOrderDto findCustomerOrderById(long id) {
        return customerOrderMapper.convertToDto(customerOrderDao.findEntityById(id).orElseThrow(() ->
                new NoSuchEntityException("ex.noSuchEntity", " (id = " + id + ")")));
    }

    @Override
    public List<CustomerOrderDto> findAllCustomerOrders(int pageNumber, int rows) {
        return customerOrderDao.findListEntities(new LinkedMultiValueMap<>(), pageNumber, rows).stream().map(customerOrderMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CustomerOrderDto createCustomerOrder(long customerId, CustomerOrderDto customerOrderDto) {
        customerService.findCustomerById(customerId);
        customerOrderDto.setCustomer(customerId);
        customerOrderDto.setPurchaseTime(dateHandler.getCurrentDate());
        List<GiftCertificateDto> certificateDtos = customerOrderDto.getGiftCertificates().stream().map(c -> certificateService.findCertificateById(c.getId()))
                .collect(Collectors.toList());
        BigDecimal amount = certificateDtos.stream().map(GiftCertificateDto::getPrice).reduce(BigDecimal::add).orElse(new BigDecimal(0));
        customerOrderDto.setGiftCertificates(certificateDtos);
        customerOrderDto.setAmount(amount);
        return customerOrderMapper.convertToDto(customerOrderDao.createEntity(customerOrderMapper.convertToEntity(customerOrderDto)));
    }
}
