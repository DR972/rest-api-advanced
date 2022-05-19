package com.epam.esm.hateoas.impl;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.controller.CustomerController;
import com.epam.esm.controller.CustomerOrderController;
import com.epam.esm.controller.TagController;
import com.epam.esm.dto.CustomerDto;
import com.epam.esm.dto.CustomerOrderDto;
import com.epam.esm.dto.ListEntitiesDto;
import com.epam.esm.hateoas.HateoasAdder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Class {@code CustomerHateoasAdder} is implementation of interface {@link HateoasAdder}
 * and intended to work with {@link CustomerDto} objects.
 *
 * @author Dzmitry Rozmysl
 * @since 1.0
 */
@Component
public class CustomerHateoasAdder implements HateoasAdder<CustomerDto> {
    private static final Class<CustomerController> CUSTOMER_CONTROLLER = CustomerController.class;
    private static final Class<CustomerOrderController> CUSTOMER_ORDER_CONTROLLER = CustomerOrderController.class;
    private static final Class<CertificateController> CERTIFICATE_CONTROLLER = CertificateController.class;
    private static final Class<TagController> TAG_CONTROLLER = TagController.class;

    @Override
    public void addLinks(CustomerDto customerDto) {
        customerDto.add(linkTo(methodOn(CUSTOMER_CONTROLLER).getCustomerById(String.valueOf(customerDto.getId()))).withSelfRel());
        customerDto.add(linkTo(methodOn(CUSTOMER_CONTROLLER).getCustomerList("5", "1")).withRel("getCustomerList"));
        customerDto.add(linkTo(methodOn(CUSTOMER_CONTROLLER).createCustomer(customerDto)).withRel("createCustomer"));

        if (!customerDto.getCustomerOrders().isEmpty()) {
            customerDto.add(linkTo(methodOn(CUSTOMER_CONTROLLER).getCustomerOrderByCustomerIdAndOrderId(String.valueOf(customerDto.getId()),
                    String.valueOf(customerDto.getCustomerOrders().get(0).getId()))).withRel("getCustomerOrderByCustomerIdAndOrderId"));
        }
        customerDto.add(linkTo(methodOn(CUSTOMER_CONTROLLER).getCustomerOrderList(String.valueOf(customerDto.getId()), "5", "1")).withRel("getCustomerOrderList"));
        customerDto.add(linkTo(methodOn(CUSTOMER_CONTROLLER).createCustomerOrder(String.valueOf(customerDto.getId()), new CustomerOrderDto())).withRel("createCustomerOrder"));

        customerDto.getCustomerOrders().forEach(o -> {
            o.add(linkTo(methodOn(CUSTOMER_ORDER_CONTROLLER).getCustomerOrderById(String.valueOf(o.getId()))).withRel("getCustomerOrderById"));
            o.getGiftCertificates().forEach(c -> {
                c.add(linkTo(methodOn(CERTIFICATE_CONTROLLER).getCertificateById(String.valueOf(c.getId()))).withRel("getCertificateById"));
                c.getTags().forEach(t -> t.add(linkTo(methodOn(TAG_CONTROLLER).getTagById(String.valueOf(t.getId()))).withRel("getTagById")));
            });
        });
    }

    @Override
    public void addLinksForListEntity(ListEntitiesDto<CustomerDto> customers, int rows, int pageNumber) {
        int numberPages = (int) Math.ceil((float) customers.getTotalNumberObjects() / rows);
        customers.getEntities().forEach(c -> c.add(linkTo(methodOn(CUSTOMER_CONTROLLER).getCustomerById(String.valueOf(c.getId()))).withRel("getCustomerById")));
        if (pageNumber < numberPages + 1) {
            customers.add(linkTo(methodOn(CUSTOMER_CONTROLLER).getCustomerById(String.valueOf(customers.getEntities().get(0).getId()))).withRel("getCustomerById"));
            customers.add(linkTo(methodOn(CUSTOMER_CONTROLLER).createCustomer(customers.getEntities().get(0))).withRel("createCustomer"));
            customers.add(linkTo(methodOn(CUSTOMER_CONTROLLER).getCustomerOrderByCustomerIdAndOrderId(String.valueOf(customers.getEntities().get(0).getId()),
                    String.valueOf(1))).withRel("getCustomerOrderByCustomerIdAndOrderId"));
            customers.add(linkTo(methodOn(CUSTOMER_CONTROLLER).createCustomerOrder(String.valueOf(customers.getEntities().get(0).getId()), new CustomerOrderDto()))
                    .withRel("createCustomerOrder"));

            customers.getEntities().get(0).getCustomerOrders().forEach(o -> {
                o.add(linkTo(methodOn(CUSTOMER_ORDER_CONTROLLER).getCustomerOrderById(String.valueOf(o.getId()))).withRel("getCustomerOrderById"));
                o.getGiftCertificates().forEach(c -> {
                    c.add(linkTo(methodOn(CERTIFICATE_CONTROLLER).getCertificateById(String.valueOf(c.getId()))).withRel("getCertificateById"));
                    c.getTags().forEach(t -> t.add(linkTo(methodOn(TAG_CONTROLLER).getTagById(String.valueOf(t.getId()))).withRel("getTagById")));
                });
            });
        }

        customers.add(linkTo(methodOn(CUSTOMER_CONTROLLER).getCustomerList("1", String.valueOf(rows))).withRel("getCustomerOrderList page 1"));
        if (pageNumber > 2 && pageNumber < numberPages + 1) {
            customers.add(linkTo(methodOn(CUSTOMER_CONTROLLER).getCustomerList(String.valueOf(pageNumber - 1), String.valueOf(rows)))
                    .withRel("getCustomerOrderList page " + (pageNumber - 1)));
        }
        if (pageNumber < numberPages - 1) {
            customers.add(linkTo(methodOn(CUSTOMER_CONTROLLER).getCustomerList(String.valueOf(pageNumber + 1), String.valueOf(rows)))
                    .withRel("getCustomerOrderList page " + (pageNumber + 1)));
        }
        customers.add(linkTo(methodOn(CUSTOMER_CONTROLLER).getCustomerList(String.valueOf(pageNumber), String.valueOf(rows))).withRel("getCustomerOrderList last page " + numberPages));
    }
}