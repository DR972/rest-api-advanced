package com.epam.esm.hateoas.impl;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.controller.CustomerController;
import com.epam.esm.controller.CustomerOrderController;
import com.epam.esm.controller.TagController;
import com.epam.esm.dto.CustomerDto;
import com.epam.esm.dto.CustomerOrderDto;
import com.epam.esm.dto.ResourceDto;
import com.epam.esm.hateoas.HateoasAdder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Class {@code CustomerOrderHateoasAdderToCustomer} is implementation of interface {@link HateoasAdder}
 * and intended to work with {@link CustomerOrderDto} objects.
 *
 * @author Dzmitry Rozmysl
 * @since 1.0
 */
@Component("customerOrderHateoasAdderToCustomer")
public class CustomerOrderHateoasAdderToCustomer implements HateoasAdder<CustomerOrderDto> {
    private static final Class<CustomerController> CUSTOMER_CONTROLLER = CustomerController.class;
    private static final Class<CustomerOrderController> CUSTOMER_ORDER_CONTROLLER = CustomerOrderController.class;
    private static final Class<CertificateController> CERTIFICATE_CONTROLLER = CertificateController.class;
    private static final Class<TagController> TAG_CONTROLLER = TagController.class;

    @Override
    public void addLinks(CustomerOrderDto customerOrderDto) {
        customerOrderDto.add(linkTo(methodOn(CUSTOMER_CONTROLLER).getCustomerOrderByCustomerIdAndOrderId(String.valueOf(customerOrderDto.getCustomerId()),
                String.valueOf(customerOrderDto.getOrderId()))).withRel("getCustomerOrderByCustomerIdAndOrderId"));
        customerOrderDto.add(linkTo(methodOn(CUSTOMER_CONTROLLER).getCustomerById(String.valueOf(customerOrderDto.getCustomerId()))).withRel("getCustomerById"));
        customerOrderDto.add(linkTo(methodOn(CUSTOMER_CONTROLLER).getCustomerList("5", "1")).withRel("getCustomerList"));
        customerOrderDto.add(linkTo(methodOn(CUSTOMER_CONTROLLER).createCustomer(new CustomerDto())).withRel("createCustomer"));
        customerOrderDto.add(linkTo(methodOn(CUSTOMER_CONTROLLER).getCustomerOrderList(String.valueOf(customerOrderDto.getCustomerId()), "5", "1")).withRel("getCustomerOrderList"));
        customerOrderDto.add(linkTo(methodOn(CUSTOMER_CONTROLLER).createCustomerOrder(String.valueOf(customerOrderDto.getCustomerId()), new CustomerOrderDto())).withRel("createCustomerOrder"));
        customerOrderDto.getGiftCertificates().forEach(c -> {
            c.add(linkTo(methodOn(CERTIFICATE_CONTROLLER).getCertificateById(String.valueOf(c.getCertificateId()))).withRel("getCertificateById"));
            c.getTags().forEach(t -> t.add(linkTo(methodOn(TAG_CONTROLLER).getTagById(String.valueOf(t.getId()))).withRel("getTagById")));
        });
    }

    @Override
    public void addLinksToListEntity(ResourceDto<CustomerOrderDto> customerOrders, int... params) {
        int rows = params[0];
        int pageNumber = params[1];
        int customerId = params[2];
        int numberPages = (int) Math.ceil((float) customerOrders.getTotalNumberObjects() / rows);
        customerOrders.add(linkTo(methodOn(CUSTOMER_CONTROLLER).getCustomerOrderList(String.valueOf(customerId), String.valueOf(pageNumber), String.valueOf(rows)))
                .withRel("getCustomerOrderList"));

        if (pageNumber < numberPages + 1) {
            customerOrders.add(linkTo(methodOn(CUSTOMER_CONTROLLER).getCustomerById(String.valueOf(customerId))).withRel("getCustomerById"));
            customerOrders.add(linkTo(methodOn(CUSTOMER_CONTROLLER).getCustomerList(String.valueOf(pageNumber), String.valueOf(rows))).withRel("getCustomerList"));
            customerOrders.add(linkTo(methodOn(CUSTOMER_CONTROLLER).createCustomer(new CustomerDto())).withRel("createCustomer"));
            customerOrders.add(linkTo(methodOn(CUSTOMER_CONTROLLER).getCustomerOrderByCustomerIdAndOrderId(String.valueOf(customerId),
                    customerOrders.getResources().get(0).getOrderId())).withRel("getCustomerOrderByCustomerIdAndOrderId"));
            customerOrders.add(linkTo(methodOn(CUSTOMER_CONTROLLER).createCustomerOrder(String.valueOf(customerId), new CustomerOrderDto())).withRel("createCustomerOrder"));

            customerOrders.getResources().forEach(o -> {
                o.add(linkTo(methodOn(CUSTOMER_ORDER_CONTROLLER).getCustomerOrderById(String.valueOf(o.getOrderId()))).withRel("getCustomerOrderById"));
                o.getGiftCertificates().forEach(c -> {
                    c.add(linkTo(methodOn(CERTIFICATE_CONTROLLER).getCertificateById(String.valueOf(c.getCertificateId()))).withRel("getCertificateById"));
                    c.getTags().forEach(t -> t.add(linkTo(methodOn(TAG_CONTROLLER).getTagById(String.valueOf(t.getId()))).withRel("getTagById")));
                });
            });
        }

        if (numberPages > 1) {
            customerOrders.add(linkTo(methodOn(CUSTOMER_CONTROLLER).getCustomerOrderList(String.valueOf(customerId), "1", String.valueOf(rows)))
                    .withRel("getCustomerOrderList page 1"));
            if (pageNumber > 2 && pageNumber < numberPages + 1) {
                customerOrders.add(linkTo(methodOn(CUSTOMER_CONTROLLER).getCustomerOrderList(String.valueOf(customerId), String.valueOf(pageNumber - 1), String.valueOf(rows)))
                        .withRel("getCustomerOrderList previous page " + (pageNumber - 1)));
            }
            if (pageNumber < numberPages - 1) {
                customerOrders.add(linkTo(methodOn(CUSTOMER_CONTROLLER).getCustomerOrderList(String.valueOf(customerId), String.valueOf(pageNumber + 1), String.valueOf(rows)))
                        .withRel("getCustomerOrderList next page " + (pageNumber + 1)));
            }
            customerOrders.add(linkTo(methodOn(CUSTOMER_CONTROLLER).getCustomerOrderList(String.valueOf(customerId), String.valueOf(pageNumber), String.valueOf(rows)))
                    .withRel("getCustomerOrderList last page " + numberPages));
        }
    }
}
