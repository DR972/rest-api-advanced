package com.epam.esm.hateoas.impl;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.controller.TagController;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.ListEntitiesDto;
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
    private static final MultiValueMap<String, String> allRequestParams = new LinkedMultiValueMap<>();

    static {
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
    }

    @Override
    public void addLinks(GiftCertificateDto certificateDto) {
        certificateDto.add(linkTo(methodOn(CERTIFICATE_CONTROLLER).getCertificateById(certificateDto.getId())).withSelfRel());
        certificateDto.add(linkTo(methodOn(CERTIFICATE_CONTROLLER).getCertificateList(allRequestParams, 5, 1)).withRel("getGiftCertificateList"));
        certificateDto.add(linkTo(methodOn(CERTIFICATE_CONTROLLER).createCertificate(certificateDto)).withRel("createGiftCertificate"));
        certificateDto.add(linkTo(methodOn(CERTIFICATE_CONTROLLER).updateCertificate(certificateDto, certificateDto.getId())).withRel("updateGiftCertificate"));
        certificateDto.add(linkTo(methodOn(CERTIFICATE_CONTROLLER).deleteCertificate(certificateDto.getId())).withRel("deleteGiftCertificate"));

        certificateDto.getTags().forEach(t -> t.add(linkTo(methodOn(TAG_CONTROLLER).getTagById(t.getId())).withRel("getTagById")));
    }

    @Override
    public void addLinksForListEntity(ListEntitiesDto<GiftCertificateDto> certificates, int rows, int pageNumber) {
        int numberPages = (int) Math.ceil((float) certificates.getTotalNumberObjects() / rows);
        certificates.getEntities().forEach(c -> c.add(linkTo(methodOn(CERTIFICATE_CONTROLLER).getCertificateById(c.getId())).withRel("getGiftCertificateById")));
        if (pageNumber < numberPages + 1) {
            certificates.add(linkTo(methodOn(CERTIFICATE_CONTROLLER).getCertificateById(certificates.getEntities().get(0).getId())).withRel("getGiftCertificateById"));
            certificates.add(linkTo(methodOn(CERTIFICATE_CONTROLLER).createCertificate(certificates.getEntities().get(0))).withRel("createGiftCertificate"));
            certificates.add(linkTo(methodOn(CERTIFICATE_CONTROLLER).updateCertificate(certificates.getEntities().get(0), certificates.getEntities().get(0).getId()))
                    .withRel("updateGiftCertificate"));
            certificates.add(linkTo(methodOn(CERTIFICATE_CONTROLLER).deleteCertificate(certificates.getEntities().get(0).getId())).withRel("deleteGiftCertificate"));
            certificates.getEntities().forEach(c -> c.getTags().forEach(t -> t.add(linkTo(methodOn(TAG_CONTROLLER).getTagById(t.getId())).withRel("getTagById"))));
        }

        certificates.add(linkTo(methodOn(CERTIFICATE_CONTROLLER).getCertificateList(allRequestParams, 1, rows)).withRel("getGiftCertificateList page 1"));
        if (pageNumber > 2 && pageNumber < numberPages + 1) {
            certificates.add(linkTo(methodOn(CERTIFICATE_CONTROLLER).getCertificateList(allRequestParams, pageNumber - 1, rows))
                    .withRel("getGiftCertificateList page " + (pageNumber - 1)));
        }
        if (pageNumber < numberPages - 1) {
            certificates.add(linkTo(methodOn(CERTIFICATE_CONTROLLER).getCertificateList(allRequestParams, pageNumber + 1, rows))
                    .withRel("getGiftCertificateList page " + (pageNumber + 1)));
        }
        certificates.add(linkTo(methodOn(CERTIFICATE_CONTROLLER).getCertificateList(allRequestParams, numberPages, rows))
                .withRel("getGiftCertificateList last page " + numberPages));
    }
}