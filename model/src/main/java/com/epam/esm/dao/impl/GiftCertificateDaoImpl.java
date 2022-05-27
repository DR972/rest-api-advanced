package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.builder.GiftCertificateQueryBuilder;
import com.epam.esm.dao.builder.GiftCertificateQueryBuilderImpl;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;

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
     * giftCertificateQueryBuilder giftCertificateQueryBuilderImpl.
     */
    private final GiftCertificateQueryBuilder giftCertificateQueryBuilderImpl;

    /**
     * The constructor creates an GiftCertificateDaoImpl object
     *
     * @param giftCertificateQueryBuilderImpl GiftCertificateQueryBuilderImpl
     */
    @Autowired
    public GiftCertificateDaoImpl(GiftCertificateQueryBuilderImpl giftCertificateQueryBuilderImpl) {
        super(GiftCertificate.class);
        this.giftCertificateQueryBuilderImpl = giftCertificateQueryBuilderImpl;
    }

    @Override
    public List<GiftCertificate> findListEntities(MultiValueMap<String, String> requestParams, int offset, int limit) {
        return giftCertificateQueryBuilderImpl.build(entityManager, requestParams).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Override
    public long countNumberEntityRows(MultiValueMap<String, String> params) {
        return giftCertificateQueryBuilderImpl.build(entityManager, params).getResultList().size();
    }
}
