package com.epam.esm.dto.mapper;

import com.epam.esm.dto.CustomerOrderDto;
import com.epam.esm.entity.CustomerOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {GiftCertificateMapper.class, CustomerOrderMapper.class})
public interface CustomerOrderMapper {
    CustomerOrderMapper INSTANCE = Mappers.getMapper(CustomerOrderMapper.class);

    @Mapping(source = "customer.id", target = "customer")
    CustomerOrderDto convertToDto(CustomerOrder customerOrder);

    @Mapping(source = "customer", target = "customer.id")
    CustomerOrder convertToEntity(CustomerOrderDto dto);
}
