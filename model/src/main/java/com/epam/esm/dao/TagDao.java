package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface {@code GiftCertificateDao} describes abstract behavior for working with
 * {@link com.epam.esm.dao.impl.TagDaoImpl} in database.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
public interface TagDao extends Dao<Tag, Long> {

    /**
     * The method finds list the most widely used tags Of Customers with the highest cost of all orders in the table `Tag`.
     *
     * @param offset int offset
     * @param limit  int limit
     * @return list of Tag objects
     */
    List<Tag> findMostPopularTag(int offset, int limit);

    /**
     * The method finds count number of rows in the list of the most popular tags.
     *
     * @return count number of rows objects
     */
    long countNumberEntityRowsInListOfMostPopularTags();

    /**
     * The method removes tags that are not associated with certificates in the `Tag` table.
     *
     * @param tags List<Tag> tags
     */
    void deleteTagNotAssociatedWithCertificates(List<Tag> tags);
}
