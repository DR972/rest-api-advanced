package com.epam.esm.dto.mapper;

import com.epam.esm.dto.CustomerOrderDto;
import com.epam.esm.entity.CustomerOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * The interface {@code CustomerMapper} generates code for mapping CustomerOrder entity and CustomerOrderDto.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@Mapper(componentModel = "spring", uses = {GiftCertificateMapper.class, CustomerOrderMapper.class})
public interface CustomerOrderMapper {
    CustomerOrderMapper INSTANCE = Mappers.getMapper(CustomerOrderMapper.class);

    /**
     * The method for converting dto to entity.
     *
     * @param customerOrder CustomerOrder customerOrder
     * @return converted dto
     */
    @Mapping(source = "customer.id", target = "customer")
    CustomerOrderDto convertToDto(CustomerOrder customerOrder);

    /**
     * The method for converting entity to dto.
     *
     * @param dto CustomerOrderDto dto
     * @return converted entity
     */
    @Mapping(source = "customer", target = "customer.id")
    CustomerOrder convertToEntity(CustomerOrderDto dto);
}
