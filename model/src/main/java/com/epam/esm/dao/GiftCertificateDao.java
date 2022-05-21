package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * The interface {@code GiftCertificateDao} describes abstract behavior for working with
 * {@link com.epam.esm.dao.impl.GiftCertificateDaoImpl} in database.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */

public interface GiftCertificateDao extends Dao<GiftCertificate, Long> {
    /**
     * The method finds list GiftCertificate objects in the table `GiftCertificate`.
     *
     * @param params     MultiValueMap<String, String> params
     * @param pageNumber int pageNumber
     * @param rows       int rows
     * @return list of GiftCertificate objects
     */
    List<GiftCertificate> findListEntities(MultiValueMap<String, String> params, int pageNumber, int rows);

    /**
     * The method finds count number of rows objects GiftCertificate in the table 'GiftCertificate'.
     *
     * @param params MultiValueMap<String, String> params
     * @return count number of rows objects
     */
    long countNumberEntityRows(MultiValueMap<String, String> params);
}
