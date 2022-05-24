package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.builder.QueryBuilderForListGiftCertificate;
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
     * queryBuilderForListGiftCertificate QueryBuilderForListGiftCertificate.
     */
    private final QueryBuilderForListGiftCertificate queryBuilderForListGiftCertificate;

    /**
     * The constructor creates an GiftCertificateDaoImpl object
     *
     * @param queryBuilderForListGiftCertificate QueryBuilderForListGiftCertificate
     */
    protected GiftCertificateDaoImpl(QueryBuilderForListGiftCertificate queryBuilderForListGiftCertificate) {
        super(GiftCertificate.class);
        this.queryBuilderForListGiftCertificate = queryBuilderForListGiftCertificate;
    }

    @Override
    public List<GiftCertificate> findListEntities(MultiValueMap<String, String> requestParams, int offset, int limit) {
        return queryBuilderForListGiftCertificate.build(entityManager, requestParams).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Override
    public long countNumberEntityRows(MultiValueMap<String, String> params) {
        return queryBuilderForListGiftCertificate.build(entityManager, params).getResultList().size();
    }
}
