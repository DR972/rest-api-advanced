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

    List<Tag> findMostWidelyUsedTagsOfCustomersWithHighestCostOfAllOrders(int pageNumber, int rows);

    void deleteGiftCertificateTagByTagId(long id);
}
