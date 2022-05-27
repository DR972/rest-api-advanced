package com.epam.esm.dao.builder;

import com.epam.esm.entity.GiftCertificate;
import org.hibernate.query.Query;
import org.springframework.util.MultiValueMap;

import javax.persistence.EntityManager;

/**
 * The interface {@code GiftCertificateQueryBuilder} describes abstract behavior for working with
 * {@link com.epam.esm.dao.builder.GiftCertificateQueryBuilderImpl} objects.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
public interface GiftCertificateQueryBuilder {
    Query<GiftCertificate> build(EntityManager entityManager, MultiValueMap<String, String> requestParams);
}
