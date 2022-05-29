package com.epam.esm.service;

import com.epam.esm.dto.ResourceDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.dto.TagDto;

/**
 * The interface {@code TagService} describes abstract behavior for working with {@link com.epam.esm.service.impl.TagServiceImpl} objects.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
public interface TagService extends BaseService<Tag, Long, TagDto> {

    /**
     * The method finds Tag by name.
     *
     * @param name Tag name
     * @return Tag object
     */
    Tag findTagByName(String name);

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
    TagDto updateTag(TagDto tagDto, String id);

    /**
     * The method performs the operation of deleting Tag.
     *
     * @param id Tag id
     */
    void deleteTag(String id);

    /**
     * The method finds list the most widely used tags Of Customers with the highest cost of all orders.
     *
     * @param pageNumber int pageNumber
     * @param rows       int rows
     * @return ResourceDto<TagDto>
     */
    ResourceDto<TagDto> findMostPopularTag(int pageNumber, int rows);
}
