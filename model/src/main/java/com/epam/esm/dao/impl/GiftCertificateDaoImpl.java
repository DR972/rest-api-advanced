package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.criteria.CriteriaQueryBuilderForGiftCertificate;
import com.epam.esm.entity.GiftCertificate;
import org.hibernate.Session;
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
    @PersistenceContext
    protected EntityManager entityManager;
    private final CriteriaQueryBuilderForGiftCertificate criteriaQueryBuilderForGiftCertificate;

    /**
     * The constructor creates an GiftCertificateDaoImpl object
     * <p>
     * //     * @param jdbcTemplate JdbcTemplate
     */
    protected GiftCertificateDaoImpl(CriteriaQueryBuilderForGiftCertificate criteriaQueryBuilderForGiftCertificate) {
        super(GiftCertificate.class);
        this.criteriaQueryBuilderForGiftCertificate = criteriaQueryBuilderForGiftCertificate;
    }

    @Override
    public List<GiftCertificate> findListEntities(MultiValueMap<String, String> params, int pageNumber, int rows) {
        return entityManager.unwrap(Session.class).createQuery(criteriaQueryBuilderForGiftCertificate.build(entityManager, params))
                .setFirstResult(pageNumber).setMaxResults(rows).getResultList();
    }
}
