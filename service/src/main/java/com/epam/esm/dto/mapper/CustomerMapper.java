package com.epam.esm.dto.mapper;

import com.epam.esm.dto.CustomerDto;
import com.epam.esm.entity.Customer;
import com.epam.esm.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * The interface {@code CustomerMapper} generates code for mapping Customer entity and CustomerDto.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@Mapper(componentModel = "spring", uses = CustomerOrderMapper.class)
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    /**
     * The method for converting dto to entity.
     *
     * @param customer Customer customer
     * @return converted dto
     */
    CustomerDto convertToDto(Customer customer);

    /**
     * The method for converting entity to dto.
     *
     * @param dto CustomerDto dto
     * @return converted entity
     */
    Customer convertToEntity(CustomerDto dto);
}
