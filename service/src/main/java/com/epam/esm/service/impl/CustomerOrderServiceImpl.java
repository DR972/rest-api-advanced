package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.AbstractDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.mapper.EntityMapper;
import com.epam.esm.entity.CustomerOrder;
import com.epam.esm.service.CustomerOrderService;
import com.epam.esm.dto.CustomerOrderDto;
import com.epam.esm.service.CustomerService;
import com.epam.esm.service.DateHandler;
import com.epam.esm.service.GiftCertificateService;
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
public class CustomerOrderServiceImpl extends AbstractService<CustomerOrder, Long, CustomerOrderDto> implements CustomerOrderService {
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
     * The constructor creates a TagServiceImpl object
     *
     * @param dao                AbstractDao<T, ID> dao
     * @param entityMapper       EntityMapper<T, D> entityMapper
     * @param dateHandler        DateHandler dateHandler
     * @param certificateService GiftCertificateService certificateService
     * @param customerService    CustomerService customerService
     */
    public CustomerOrderServiceImpl(AbstractDao<CustomerOrder, Long> dao, EntityMapper<CustomerOrder, CustomerOrderDto> entityMapper,
                                    DateHandler dateHandler, GiftCertificateService certificateService, CustomerService customerService) {
        super(dao, entityMapper);
        this.dateHandler = dateHandler;
        this.certificateService = certificateService;
        this.customerService = customerService;
    }

    @Override
    @Transactional
    public CustomerOrderDto createCustomerOrder(String customerId, CustomerOrderDto customerOrderDto) {
        customerService.findEntityById(customerId);
        customerOrderDto.setCustomerId(customerId);
        customerOrderDto.setPurchaseTime(dateHandler.getCurrentDate());
        List<GiftCertificateDto> certificateDtos = customerOrderDto.getGiftCertificates().stream().map(c -> certificateService.findEntityById(c.getCertificateId()))
                .distinct().collect(Collectors.toList());
        BigDecimal amount = certificateDtos.stream().map(c -> new BigDecimal(c.getPrice().replace(',', '.'))).reduce(BigDecimal::add).orElse(new BigDecimal(0));
        customerOrderDto.setGiftCertificates(certificateDtos);
        customerOrderDto.setAmount(amount);
        return entityMapper.convertToDto(dao.createEntity(entityMapper.convertToEntity(customerOrderDto)));
    }
}
