package com.epam.esm.hateoas.impl;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.controller.CustomerController;
import com.epam.esm.controller.CustomerOrderController;
import com.epam.esm.controller.TagController;
import com.epam.esm.dto.CustomerDto;
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
        customerDto.add(linkTo(methodOn(CUSTOMER_CONTROLLER).getCustomerById(customerDto.getId())).withSelfRel());
        customerDto.add(linkTo(methodOn(CUSTOMER_CONTROLLER).getCustomerList(5, 1)).withRel("getCustomerList"));
        customerDto.add(linkTo(methodOn(CUSTOMER_CONTROLLER).createCustomer(customerDto)).withRel("createCustomer"));

        customerDto.add(linkTo(methodOn(CUSTOMER_CONTROLLER).getCustomerOrderByCustomerIdAndOrderId(customerDto.getId(),
                1)).withRel("getCustomerOrderByCustomerIdAndOrderId"));

        customerDto.add(linkTo(methodOn(CUSTOMER_CONTROLLER).getCustomerOrderList(customerDto.getId(), 5, 1)).withRel("getCustomerOrderList"));
        if (!customerDto.getCustomerOrders().isEmpty()) {
            customerDto.add(linkTo(methodOn(CUSTOMER_CONTROLLER).createCustomerOrder(customerDto.getId(), customerDto.getCustomerOrders().get(0))).withRel("createCustomerOrder"));
        }

        customerDto.getCustomerOrders().forEach(o -> {
            o.add(linkTo(methodOn(CUSTOMER_ORDER_CONTROLLER).getCustomerOrderById(o.getId())).withRel("getCustomerOrderById"));
            o.getGiftCertificates().forEach(c -> {
                c.add(linkTo(methodOn(CERTIFICATE_CONTROLLER).getCertificateById(c.getId())).withRel("getCertificateById"));
                c.getTags().forEach(t -> t.add(linkTo(methodOn(TAG_CONTROLLER).getTagById(t.getId())).withRel("getTagById")));
            });
        });
    }
}