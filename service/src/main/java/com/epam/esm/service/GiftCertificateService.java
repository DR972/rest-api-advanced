package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.ListEntitiesDto;
import org.springframework.util.MultiValueMap;

/**
 * The interface {@code GiftCertificateService} describes abstract behavior for working with
 * {@link com.epam.esm.service.impl.GiftCertificateServiceImpl} objects.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
public interface GiftCertificateService {

    /**
     * The method finds GiftCertificate.
     *
     * @param id GiftCertificateDto id
     * @return GiftCertificateDto object
     */
    GiftCertificateDto findCertificateById(long id);

    /**
     * The method finds list GiftCertificates.
     *
     * @param params     MultiValueMap<String, String> all request params
     * @param pageNumber int pageNumber
     * @param rows       int rows
     * @return ListEntitiesDto<GiftCertificateDto>
     */
    ListEntitiesDto<GiftCertificateDto> findListCertificates(MultiValueMap<String, String> params, int pageNumber, int rows);

    /**
     * The method performs the operation of saving GiftCertificate.
     *
     * @param certificateDto GiftCertificateDto
     * @return GiftCertificateDto object
     */
    GiftCertificateDto createCertificate(GiftCertificateDto certificateDto);

    /**
     * The method performs the operation of updating GiftCertificate.
     *
     * @param certificateDto new GiftCertificateDto parameters
     * @param id             GiftCertificateDto id
     * @return GiftCertificateDto object
     */
    GiftCertificateDto updateCertificate(GiftCertificateDto certificateDto, long id);

    /**
     * The method performs the operation of deleting GiftCertificate.
     *
     * @param id GiftCertificateDto id
     */
    void deleteCertificate(long id);
}
