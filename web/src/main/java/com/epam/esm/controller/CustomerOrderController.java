package com.epam.esm.controller;

import com.epam.esm.dto.ListEntitiesDto;
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

import javax.validation.constraints.Digits;

/**
 * Class {@code CustomerOrderController} is an endpoint of the API which allows you to perform operations on Customer Orders.
 * Annotated by {@link RestController} without parameters to provide an answer in application/json.
 * Annotated by {@link RequestMapping} with parameter value = "/orders".
 * Annotated by {@link Validated} without parameters  provides checking of constraints in method parameters.
 * So that {@code TagController} is accessed by sending request to /orders.
 *
 * @author Dzmitry Rozmysl
 * @since 1.0
 */
@RestController
@RequestMapping("/orders")
@Validated
public class CustomerOrderController {
    private static final String ROWS = "rows";
    private static final String PAGE_NUMBER = "pageNumber";
    /**
     * CustomerOrderService customerOrderService.
     */
    private final CustomerOrderService customerOrderService;
    /**
     * HateoasAdder<CustomerOrderDto> hateoasAdder.
     */
    private final HateoasAdder<CustomerOrderDto> hateoasAdder;

    /**
     * The constructor creates a CustomerOrderController object
     *
     * @param customerOrderService CustomerOrderService customerOrderService
     * @param hateoasAdder         HateoasAdder<CustomerOrderDto> hateoasAdder
     */
    @Autowired
    public CustomerOrderController(CustomerOrderService customerOrderService, HateoasAdder<CustomerOrderDto> hateoasAdder) {
        this.customerOrderService = customerOrderService;
        this.hateoasAdder = hateoasAdder;
    }


    /**
     * Method for getting CustomerOrderDto by ID.
     *
     * @param id CustomerOrderDto id
     * @return CustomerOrderDto
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerOrderDto getCustomerOrderById(@PathVariable @Digits(integer = 9, fraction = 0, message = "ex.customerOrderIdPositive") String id) {
        CustomerOrderDto customerOrderDto = customerOrderService.findCustomerOrderById(Long.parseLong(id));
        hateoasAdder.addLinks(customerOrderDto);
        return customerOrderDto;
    }


    /**
     * Method for getting list of all CustomerOrderDto objects.
     *
     * @param rows       number of lines per page (5 by default)
     * @param pageNumber page number(default 0)
     * @return ListEntitiesDto<CustomerOrderDto>
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ListEntitiesDto<CustomerOrderDto> getCustomerOrderList(@RequestParam(name = PAGE_NUMBER, defaultValue = "1") @Digits(integer = 6, fraction = 0, message = "ex.page") String pageNumber,
                                                                  @RequestParam(name = ROWS, defaultValue = "5") @Digits(integer = 6, fraction = 0, message = "ex.rows") String rows) {
        ListEntitiesDto<CustomerOrderDto> orders = customerOrderService.findListCustomerOrders(Integer.parseInt(pageNumber), Integer.parseInt(rows));
        hateoasAdder.addLinksForListEntity(orders, Integer.parseInt(rows), Integer.parseInt(pageNumber));
        return orders;
    }
}
