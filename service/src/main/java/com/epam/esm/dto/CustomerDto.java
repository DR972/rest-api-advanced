package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto extends RepresentationModel<CustomerDto> {
    private long id;
    @NotNull(message = "ex.customerNameNotNull")
    @Size(min = 2, max = 30, message = "ex.customerNameSize")
    private String name;

    private List<CustomerOrderDto> customerOrders = new ArrayList<>();

    public CustomerDto(String name) {
        this.name = name;
    }
}
