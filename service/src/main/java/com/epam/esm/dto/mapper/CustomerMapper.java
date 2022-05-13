package com.epam.esm.dto.mapper;

import com.epam.esm.dto.CustomerDto;
import com.epam.esm.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = "spring", uses = CustomerOrderMapper.class)
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDto convertToDto(Customer customer);

    Customer convertToEntity(CustomerDto dto);
}
