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
@Repository
public interface TagDao extends Dao<Tag, Long> {

    /**
     * The method finds list the most widely used tags Of Customers with the highest cost of all orders in the table `Tag`.
     *
     * @param pageNumber int pageNumber
     * @param rows       int rows
     * @return list of Tag objects
     */
    List<Tag> findMostWidelyUsedTagsOfCustomersWithHighestCostOfAllOrders(int pageNumber, int rows);

    /**
     * The method performs operation of deleting on the object GiftCertificateTag in the table 'GiftCertificateTag'.
     *
     * @param id long id
     */
    void deleteGiftCertificateTagByTagId(long id);
}
