package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The class {@code TagDaoImpl} is implementation of interface {@link TagDao} and intended to work with {@link com.epam.esm.entity.Tag} table.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@Repository
public class TagDaoImpl extends AbstractDao<Tag, Long> implements TagDao {
    private static final String QUERY_MOST_POPULAR_TAGS = "SELECT t FROM CustomerOrder o LEFT JOIN o.giftCertificates g LEFT JOIN g.tags t WHERE o.customer IN " +
            "(SELECT c.id FROM Customer c LEFT JOIN CustomerOrder o ON c.id = o.customer GROUP BY c.id " +
            "HAVING (SUM (o.amount) >= ALL (SELECT SUM (o.amount) FROM Customer c LEFT JOIN CustomerOrder o ON c.id = o.customer GROUP BY c.id))) " +
            "GROUP BY t.id " +
            "HAVING (count(t.id) >= ALL (SELECT count(t.id) FROM CustomerOrder o LEFT JOIN o.giftCertificates g LEFT JOIN g.tags t WHERE o.customer IN " +
            "(SELECT c.id FROM Customer c LEFT JOIN CustomerOrder o ON c.id = o.customer GROUP BY c.id " +
            "HAVING (SUM (o.amount) >= ALL (SELECT SUM (o.amount) FROM Customer c LEFT JOIN CustomerOrder o ON c.id = o.customer GROUP BY c.id))) " +
            "GROUP BY t.id))";

    /**
     * EntityManager entityManager.
     */
    @PersistenceContext
    protected EntityManager entityManager;

    /**
     * The constructor creates an TagDaoImpl object
     */
    public TagDaoImpl() {
        super(Tag.class);
    }


    @Override
    public List<Tag> findMostWidelyUsedTagsOfCustomersWithHighestCostOfAllOrders(int pageNumber, int rows) {
        return entityManager.unwrap(Session.class).createQuery(QUERY_MOST_POPULAR_TAGS, Tag.class).setFirstResult(pageNumber).setMaxResults(rows).getResultList();
    }

    @Override
    public long countNumberEntityRowsInListOfMostPopularTags() {
        return entityManager.unwrap(Session.class).createQuery(QUERY_MOST_POPULAR_TAGS, Tag.class).getResultList().size();
    }

    @Override
    public void deleteTagNotAssociatedWithCertificates(List<Tag> tags) {
        String tageName = tags.stream().map(Tag::getName).collect(Collectors.joining(",", "'", "'"));
        String query = "DELETE FROM tag WHERE tag.name IN (SELECT tag.name FROM unnest(string_to_array(" + tageName + ", ',')) u " +
                "LEFT JOIN tag ON tag.name=u LEFT JOIN gift_certificate_tag ON tag.id = tag_id WHERE tag_id is NULL)";
        entityManager.unwrap(Session.class).createNativeQuery(query).executeUpdate();
    }
}