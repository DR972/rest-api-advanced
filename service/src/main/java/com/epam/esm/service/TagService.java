package com.epam.esm.service;

import com.epam.esm.dto.ListEntitiesDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.dto.TagDto;

import java.util.List;

/**
 * The interface {@code TagService} describes abstract behavior for working with {@link com.epam.esm.service.impl.TagServiceImpl} objects.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
public interface TagService {

    /**
     * The method finds Tag by id.
     *
     * @param id TagDto id
     * @return TagDto object
     */
    TagDto findTagById(long id);

    /**
     * The method finds Tag by name.
     *
     * @param name Tag name
     * @return Tag object
     */
    Tag findTagByName(String name);

    /**
     * The method finds list Tags.
     *
     * @param pageNumber int pageNumber
     * @param rows       int rows
     * @return ListEntitiesDto<TagDto>
     */
    ListEntitiesDto<TagDto> findListTags(int pageNumber, int rows);

    /**
     * The method performs the operation of saving Tag.
     *
     * @param tagDto TagDto
     * @return TagDto object
     */
    TagDto createTag(TagDto tagDto);

    /**
     * The method performs the operation of updating Tag.
     *
     * @param tagDto new TagDto parameters
     * @param id     TagDto id
     * @return TagDto object
     */
    TagDto updateTag(TagDto tagDto, long id);

    /**
     * The method performs the operation of deleting Tag.
     *
     * @param id Tag id
     */
    void deleteTag(long id);

    /**
     * The method finds list the most widely used tags Of Customers with the highest cost of all orders.
     *
     * @param pageNumber int pageNumber
     * @param rows       int rows
     * @return List<TagDto>
     */
    List<TagDto> findMostWidelyUsedTagsOfCustomersWithHighestCostOfAllOrders(int pageNumber, int rows);
}
