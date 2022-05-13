package com.epam.esm.dao.criteria;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Component
public class CriteriaQueryBuilderForGiftCertificate {
    private static final String TAGS = "tags";
    private static final String NAME = "name";
    private static final String TAG = "tag";
    private static final String SORTING = "sorting";
    private static final String DESCRIPTION = "description";

    public CriteriaQuery<GiftCertificate> build(EntityManager entityManager, MultiValueMap<String, String> params) {
        CriteriaBuilder criteriaBuilder = entityManager.unwrap(Session.class).getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = criteriaQuery.from(GiftCertificate.class);

        Subquery<Tag> subQuery = criteriaQuery.subquery(Tag.class);
        Root<GiftCertificate> subRoot = subQuery.correlate(root);
        Join<GiftCertificate, Tag> tagJoin = subRoot.join(TAGS);

        Predicate[] predicates = Stream.concat(Arrays.stream(searchByDescriptionOrName(criteriaBuilder, root, NAME, params.get(NAME))),
                Stream.concat(Arrays.stream(searchByDescriptionOrName(criteriaBuilder, root, DESCRIPTION, params.get(DESCRIPTION))),
                        Arrays.stream(searchByTag(criteriaBuilder, tagJoin, params.get(TAG))))).toArray(Predicate[]::new);

        return criteriaQuery.where(criteriaBuilder.exists(subQuery.select(tagJoin).where(predicates))).orderBy(buildOrder(criteriaBuilder, root, params.get(SORTING)));
    }

    private List<Order> buildOrder(CriteriaBuilder criteriaBuilder, Root<GiftCertificate> root, List<String> orders) {
        if (orders != null) {
            List<Order> orderList = new ArrayList<>();
            orders.forEach(o -> {
                if (o.startsWith("-")) {
                    orderList.add(criteriaBuilder.desc(root.get(o.substring(1))));
                } else orderList.add(criteriaBuilder.asc(root.get(o)));
            });
            return orderList;
        } else return new ArrayList<>();
    }

    private Predicate[] searchByDescriptionOrName(CriteriaBuilder criteriaBuilder, Root<GiftCertificate> root, String paramName, List<String> params) {
        if (params != null) {
            return params.stream().map(n -> criteriaBuilder.like(root.get(paramName), "%" + n + "%")).toArray(Predicate[]::new);
        } else return new Predicate[0];
    }

    private Predicate[] searchByTag(CriteriaBuilder criteriaBuilder, Join<GiftCertificate, Tag> tagJoin, List<String> tags) {
        if (tags != null) {
            return tags.stream().map(t -> criteriaBuilder.equal(tagJoin.get(NAME), t)).toArray(Predicate[]::new);
        } else return new Predicate[0];
    }
}