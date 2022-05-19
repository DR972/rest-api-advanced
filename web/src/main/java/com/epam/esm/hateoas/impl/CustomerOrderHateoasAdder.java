package com.epam.esm.hateoas.impl;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.controller.CustomerOrderController;
import com.epam.esm.controller.TagController;
import com.epam.esm.dto.CustomerOrderDto;
import com.epam.esm.dto.ListEntitiesDto;
import com.epam.esm.hateoas.HateoasAdder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Class {@code CustomerOrderHateoasAdder} is implementation of interface {@link HateoasAdder}
 * and intended to work with {@link CustomerOrderDto} objects.
 *
 * @author Dzmitry Rozmysl
 * @since 1.0
 */
@Component
public class CustomerOrderHateoasAdder implements HateoasAdder<CustomerOrderDto> {
    private static final Class<CustomerOrderController> CUSTOMER_ORDER_CONTROLLER = CustomerOrderController.class;
    private static final Class<CertificateController> CERTIFICATE_CONTROLLER = CertificateController.class;
    private static final Class<TagController> TAG_CONTROLLER = TagController.class;

    @Override
    public void addLinks(CustomerOrderDto customerOrderDto) {
        customerOrderDto.add(linkTo(methodOn(CUSTOMER_ORDER_CONTROLLER).getCustomerOrderById(customerOrderDto.getId())).withSelfRel());
        customerOrderDto.add(linkTo(methodOn(CUSTOMER_ORDER_CONTROLLER).getCustomerOrderList(5, 1)).withRel("getCustomerOrderList"));

        customerOrderDto.getGiftCertificates().forEach(c -> {
            c.add(linkTo(methodOn(CERTIFICATE_CONTROLLER).getCertificateById(c.getId())).withRel("getCertificateById"));
            c.getTags().forEach(t -> t.add(linkTo(methodOn(TAG_CONTROLLER).getTagById(t.getId())).withRel("getTagById")));
        });
    }

    @Override
    public void addLinksForListEntity(ListEntitiesDto<CustomerOrderDto> customerOrders, int rows, int pageNumber) {
        int numberPages = (int) Math.ceil((float) customerOrders.getTotalNumberObjects() / rows);
        customerOrders.getEntities().forEach(c -> c.add(linkTo(methodOn(CUSTOMER_ORDER_CONTROLLER).getCustomerOrderById(c.getId())).withRel("getCustomerOrderById")));
        if (pageNumber < numberPages + 1) {
            customerOrders.add(linkTo(methodOn(CUSTOMER_ORDER_CONTROLLER).getCustomerOrderById(customerOrders.getEntities().get(0).getId())).withRel("getCustomerOrderById"));
            customerOrders.getEntities().forEach(o -> o.getGiftCertificates().forEach(c -> {
                c.add(linkTo(methodOn(CERTIFICATE_CONTROLLER).getCertificateById(c.getId())).withRel("getCertificateById"));
                c.getTags().forEach(t -> t.add(linkTo(methodOn(TAG_CONTROLLER).getTagById(t.getId())).withRel("getTagById")));
            }));
        }

        customerOrders.add(linkTo(methodOn(CUSTOMER_ORDER_CONTROLLER).getCustomerOrderList(1, rows)).withRel("getCustomerOrderList page 1"));
        if (pageNumber > 2 && pageNumber < numberPages + 1) {
            customerOrders.add(linkTo(methodOn(CUSTOMER_ORDER_CONTROLLER).getCustomerOrderList(pageNumber - 1, rows))
                    .withRel("getCustomerOrderList page " + (pageNumber - 1)));
        }
        if (pageNumber < numberPages - 1) {
            customerOrders.add(linkTo(methodOn(CUSTOMER_ORDER_CONTROLLER).getCustomerOrderList(pageNumber + 1, rows))
                    .withRel("getCustomerOrderList page " + (pageNumber + 1)));
        }
        customerOrders.add(linkTo(methodOn(CUSTOMER_ORDER_CONTROLLER).getCustomerOrderList(numberPages, rows))
                .withRel("getCustomerOrderList last page " + numberPages));
    }
}
