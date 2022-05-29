package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * The class {@code CustomerDto} represents CustomerDto.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto extends RepresentationModel<CustomerDto> implements BaseEntityDto {
    /**
     * String customerId.
     */
    @Null(message = "ex.customerIdNull")
    private String customerId;

    /**
     * String name.
     */
    @NotNull(message = "ex.customerNameNotNull")
    @Pattern(regexp = "^[A-Za-z]+[\\w+\\s?]+\\w${2,30}", message = "ex.customerName")
    private String name;

    /**
     * List<CustomerOrderDto> customerOrders.
     */
    @Size(max = 0, message = "ex.customerOrdersNull")
    private List<CustomerOrderDto> customerOrders = new ArrayList<>();
}
