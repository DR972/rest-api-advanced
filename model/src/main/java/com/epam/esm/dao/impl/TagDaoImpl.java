package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * The class {@code TagDaoImpl} is implementation of interface {@link TagDao} and intended to work with {@link com.epam.esm.entity.Tag} table.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@Repository
public class TagDaoImpl extends AbstractDao<Tag, Long> implements TagDao {
    @PersistenceContext
    protected EntityManager entityManager;

    public TagDaoImpl() {
        super(Tag.class);
    }

    @Override
    public void createNewTag(String tagName) {
        String query = "INSERT INTO tag (name)  SELECT :name WHERE NOT EXISTS (SELECT name FROM tag WHERE name = :name)";
        entityManager.unwrap(Session.class).createNativeQuery(query, Tag.class)
                .setParameter("name", tagName)
                .executeUpdate();
    }

    @Override
    public List<Tag> findListTags(int pageNumber, int rows) {
        String query = "SELECT t FROM CustomerOrder o LEFT JOIN o.giftCertificates g LEFT JOIN g.tags t WHERE o.customer in " +
                "(SELECT c.id FROM Customer c LEFT JOIN CustomerOrder o on c.id = o.customer GROUP BY c.id " +
                "HAVING (SUM (o.amount) >= ALL (SELECT SUM (o.amount) FROM Customer c LEFT JOIN CustomerOrder o ON c.id = o.customer GROUP BY c.id))) " +
                "GROUP BY t.id " +
                "HAVING (count(t.id) >= ALL (SELECT count(t.id) FROM CustomerOrder o LEFT JOIN o.giftCertificates g LEFT JOIN g.tags t WHERE o.customer in " +
                "(SELECT c.id FROM Customer c LEFT JOIN CustomerOrder o on c.id = o.customer GROUP BY c.id " +
                "HAVING (SUM (o.amount) >= ALL (SELECT SUM (o.amount) FROM Customer c LEFT JOIN CustomerOrder o ON c.id = o.customer GROUP BY c.id))) " +
                "GROUP BY t.id))";
        return entityManager.unwrap(Session.class).createQuery(query, Tag.class).setFirstResult(pageNumber).setMaxResults(rows).getResultList();
    }

    @Override
    public void deleteGiftCertificateTagByTagId(long id) {
        System.out.println("deleteGiftCertificateTagByTagId " + id);
        entityManager.unwrap(Session.class).createNativeQuery("DELETE FROM gift_certificate_tag WHERE tag_id = :id")
                .setParameter("id", id).executeUpdate();
        entityManager.flush();
        entityManager.clear();
    }
}
