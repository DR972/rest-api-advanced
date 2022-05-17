package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.builder.QueryBuilderForGiftCertificate;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * The class {@code GiftCertificateDaoImpl} is implementation of interface {@link GiftCertificateDao}
 * and intended to work with {@link com.epam.esm.entity.GiftCertificate} table.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@Repository
public class GiftCertificateDaoImpl extends AbstractDao<GiftCertificate, Long> implements GiftCertificateDao {
    /**
     * EntityManager entityManager.
     */
    @PersistenceContext
    protected EntityManager entityManager;
    /**
     * queryBuilderForGiftCertificate QueryBuilderForGiftCertificate.
     */
    private final QueryBuilderForGiftCertificate queryBuilderForGiftCertificate;

    /**
     * The constructor creates an GiftCertificateDaoImpl object
     *
     * @param queryBuilderForGiftCertificate QueryBuilderForGiftCertificate
     */
    protected GiftCertificateDaoImpl(QueryBuilderForGiftCertificate queryBuilderForGiftCertificate) {
        super(GiftCertificate.class);
        this.queryBuilderForGiftCertificate = queryBuilderForGiftCertificate;
    }

    @Override
    public List<GiftCertificate> findListEntities(MultiValueMap<String, String> requestParams, int pageNumber, int rows) {
        return queryBuilderForGiftCertificate.build(entityManager, requestParams).setFirstResult(pageNumber).setMaxResults(rows).getResultList();

    }
}
