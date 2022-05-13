package com.epam.esm.controller;

import com.epam.esm.hateoas.HateoasAdder;
import com.epam.esm.service.CustomerOrderService;
import com.epam.esm.dto.CustomerOrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/orders")
@Validated
public class CustomerOrderController {
    private static final String ROWS = "rows";
    private static final String PAGE_NUMBER = "pageNumber";
    /**
     * TagService tagService.
     */
    private final CustomerOrderService customerOrderService;
    private final HateoasAdder<CustomerOrderDto> hateoasAdder;

    @Autowired
    public CustomerOrderController(CustomerOrderService customerOrderService, HateoasAdder<CustomerOrderDto> hateoasAdder) {
        this.customerOrderService = customerOrderService;
        this.hateoasAdder = hateoasAdder;
    }


    /**
     * Method for getting TagDto by ID.
     *
     * @param id TagDto id
     * @return TagDto
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerOrderDto getCustomerOrderById(@PathVariable @Positive(message = "ex.customerOrderIdPositive") long id) {
        CustomerOrderDto customerOrderDto = customerOrderService.findCustomerOrderById(id);
        hateoasAdder.addLinks(customerOrderDto);
        return customerOrderDto;
    }


    /**
     * Method for getting list of all TagDto objects.
     *
     * @return list of TagDto objects
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerOrderDto> getCustomerOrderList(@RequestParam(name = ROWS, defaultValue = "5") @Positive(message = "ex.rows") int rows,
                                                       @RequestParam(name = PAGE_NUMBER, defaultValue = "1") @Positive(message = "ex.page") int pageNumber) {
        List<CustomerOrderDto> customerOrders = customerOrderService.findAllCustomerOrders((pageNumber - 1) * rows, rows);
        customerOrders.forEach(hateoasAdder::addLinks);
        return customerOrders;
    }
}
