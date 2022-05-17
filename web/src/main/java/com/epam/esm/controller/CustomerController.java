package com.epam.esm.controller;

import com.epam.esm.dto.CustomerOrderDto;
import com.epam.esm.hateoas.HateoasAdder;
import com.epam.esm.service.CustomerOrderService;
import com.epam.esm.service.CustomerService;
import com.epam.esm.dto.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * Class {@code CustomerController} is an endpoint of the API which allows you to perform operations on Customers.
 * Annotated by {@link RestController} without parameters to provide an answer in application/json.
 * Annotated by {@link RequestMapping} with parameter value = "/customers".
 * Annotated by {@link Validated} without parameters  provides checking of constraints in method parameters.
 * So that {@code CustomerController} is accessed by sending request to /customers.
 *
 * @author Dzmitry Rozmysl
 * @since 1.0
 */
@RestController
@RequestMapping("/customers")
@Validated
public class CustomerController {
    private static final String ROWS = "rows";
    private static final String PAGE_NUMBER = "pageNumber";
    /**
     * CustomerService customerService.
     */
    private final CustomerService customerService;
    /**
     * CustomerOrderService customerOrderService.
     */
    private final CustomerOrderService customerOrderService;
    /**
     * HateoasAdder<CustomerDto> customerHateoasAdder.
     */
    private final HateoasAdder<CustomerDto> customerHateoasAdder;
    /**
     * HateoasAdder<CustomerOrderDto> orderHateoasAdder.
     */
    private final HateoasAdder<CustomerOrderDto> orderHateoasAdder;

    /**
     * The constructor creates a CustomerController object
     *
     * @param customerService      CustomerService customerService
     * @param customerOrderService CustomerOrderService customerOrderService
     * @param customerHateoasAdder HateoasAdder<CustomerDto> customerHateoasAdder
     * @param orderHateoasAdder    HateoasAdder<CustomerOrderDto> orderHateoasAdder
     */
    @Autowired
    public CustomerController(CustomerService customerService, CustomerOrderService customerOrderService, HateoasAdder<CustomerDto> customerHateoasAdder,
                              HateoasAdder<CustomerOrderDto> orderHateoasAdder) {
        this.customerService = customerService;
        this.customerOrderService = customerOrderService;
        this.customerHateoasAdder = customerHateoasAdder;
        this.orderHateoasAdder = orderHateoasAdder;
    }

    /**
     * Method for getting CustomerDto by ID.
     *
     * @param id CustomerDto id
     * @return CustomerDto
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDto getCustomerById(@PathVariable @Positive(message = "ex.customerIdPositive") long id) {
        CustomerDto customerDto = customerService.findCustomerById(id);
        customerHateoasAdder.addLinks(customerDto);
        return customerDto;
    }

    /**
     * Method for getting list of all CustomerDto objects.
     *
     * @param rows       number of lines per page (5 by default)
     * @param pageNumber page number(default 0)
     * @return list of CustomerDto objects
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDto> getCustomerList(@RequestParam(name = ROWS, defaultValue = "5") @Positive(message = "ex.rows") int rows,
                                             @RequestParam(name = PAGE_NUMBER, defaultValue = "1") @Positive(message = "ex.page") int pageNumber) {
        List<CustomerDto> customers = customerService.findListCustomers((pageNumber - 1) * rows, rows);
        customers.forEach(customerHateoasAdder::addLinks);
        return customers;
    }

    /**
     * Method for saving new CustomerDto.
     * Annotated by {@link Validated} provides validation of the fields of the CustomerDto object when creating.
     *
     * @param customer CustomerDto
     * @return created TagDto
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto createCustomer(@Valid @RequestBody CustomerDto customer) {
        CustomerDto customerDto = customerService.createCustomer(customer);
        customerHateoasAdder.addLinks(customerDto);
        return customerDto;
    }

    /**
     * Method for getting CustomerOrderDto by Customer id and CustomerOrder id.
     *
     * @param customerId long customerId
     * @param orderId    long orderId
     * @return CustomerOrderDto
     */
    @GetMapping("{customerId}/orders/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerOrderDto getCustomerOrderByCustomerIdAndOrderId(@PathVariable @Positive(message = "ex.customerIdPositive") long customerId,
                                                                   @PathVariable @Positive(message = "ex.customerOrderIdPositive") long orderId) {
        CustomerOrderDto customerOrderDto = customerService.findCustomerOrderByCustomerIdAndOrderId(customerId, orderId);
        orderHateoasAdder.addLinks(customerOrderDto);
        return customerOrderDto;
    }

    /**
     * Method for getting list of CustomerOrderDto objects.
     *
     * @param customerId long customerId
     * @param rows       number of lines per page (5 by default)
     * @param pageNumber page number(default 0)
     * @return list of CustomerOrderDto objects
     */
    @GetMapping("{customerId}/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerOrderDto> getCustomerOrderList(@PathVariable @Positive(message = "ex.customerIdPositive") long customerId,
                                                       @RequestParam(name = ROWS, defaultValue = "5") @Positive(message = "ex.rows") int rows,
                                                       @RequestParam(name = PAGE_NUMBER, defaultValue = "1") @Positive(message = "ex.page") int pageNumber) {
        List<CustomerOrderDto> customerOrders = customerService.findAllCustomerOrdersByCustomerId(customerId, (pageNumber - 1) * rows, rows);
        customerOrders.forEach(orderHateoasAdder::addLinks);
        return customerOrders;
    }

    /**
     * Method for saving new CustomerOrderDto.
     * Annotated by {@link Validated} with parameters CustomerOrderDto.OnCreate.class provides validation of the fields of the CustomerOrderDto object when creating.
     *
     * @param customerId    long customerId
     * @param customerOrder CustomerOrderDto customerOrder
     * @return created TagDto
     */
    @PostMapping("{customerId}/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerOrderDto createCustomerOrder(@PathVariable @Positive(message = "ex.customerIdPositive") long customerId,
                                                @Validated(CustomerOrderDto.OnCreate.class) @RequestBody CustomerOrderDto customerOrder) {
        CustomerOrderDto customerOrderDto = customerOrderService.createCustomerOrder(customerId, customerOrder);
        orderHateoasAdder.addLinks(customerOrderDto);
        return customerOrderDto;
    }
}
