package com.epam.esm.hateoas.impl;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.controller.TagController;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.hateoas.HateoasAdder;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Class {@code GiftCertificateHateoasAdder} is implementation of interface {@link HateoasAdder}
 * and intended to work with {@link GiftCertificateDto} objects.
 *
 * @author Dzmitry Rozmysl
 * @since 1.0
 */
@Component
public class GiftCertificateHateoasAdder implements HateoasAdder<GiftCertificateDto> {
    private static final Class<CertificateController> CERTIFICATE_CONTROLLER = CertificateController.class;
    private static final Class<TagController> TAG_CONTROLLER = TagController.class;
    private static final String NAME = "name";
    private static final String TAG = "tag";
    private static final String SORTING = "sorting";
    private static final String DESCRIPTION = "description";

    @Override
    public void addLinks(GiftCertificateDto certificateDto) {
        MultiValueMap<String, String> allRequestParams = new LinkedMultiValueMap<>();
        allRequestParams.add(SORTING, "lastUpdateDate");
        allRequestParams.add(SORTING, "name");
        allRequestParams.add(SORTING, "id");
        allRequestParams.add(SORTING, "price");
        allRequestParams.add(SORTING, "lastUpdateDate");
        allRequestParams.add(TAG, "nature");
        allRequestParams.add(TAG, "horse");
        allRequestParams.add(NAME, "riding");
        allRequestParams.add(NAME, "ATV");
        allRequestParams.add(DESCRIPTION, "riding");
        allRequestParams.add(DESCRIPTION, "ATV");

        certificateDto.add(linkTo(methodOn(CERTIFICATE_CONTROLLER).getCertificateById(certificateDto.getId())).withSelfRel());
        certificateDto.add(linkTo(methodOn(CERTIFICATE_CONTROLLER).getCertificateList(allRequestParams, 5, 1)).withRel("getGiftCertificateList"));
        certificateDto.add(linkTo(methodOn(CERTIFICATE_CONTROLLER).createCertificate(certificateDto)).withRel("createGiftCertificate"));
        certificateDto.add(linkTo(methodOn(CERTIFICATE_CONTROLLER).updateCertificate(certificateDto, certificateDto.getId())).withRel("updateGiftCertificate"));
        certificateDto.add(linkTo(methodOn(CERTIFICATE_CONTROLLER).deleteCertificate(certificateDto.getId())).withRel("deleteGiftCertificate"));

        certificateDto.getTags().forEach(t -> t.add(linkTo(methodOn(TAG_CONTROLLER).getTagById(t.getId())).withRel("getTagById")));
    }
}