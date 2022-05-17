package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.mapper.TagMapper;
import com.epam.esm.exception.DuplicateEntityException;
import com.epam.esm.exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The class {@code TagServiceImpl} is implementation of interface {@link TagService} and provides logic to work with {@link com.epam.esm.entity.Tag}.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@Service
public class TagServiceImpl implements TagService {
    /**
     * TagDao tagDao.
     */
    private final TagDao tagDao;

    /**
     * TagDao tagDao.
     */
    private final TagMapper tagMapper;

    /**
     * The constructor creates a TagServiceImpl object
     *
     * @param tagDao    TagDao tagDao
     * @param tagMapper TagMapper tagMapper
     */
    @Autowired
    public TagServiceImpl(TagDao tagDao, TagMapper tagMapper) {
        this.tagDao = tagDao;
        this.tagMapper = tagMapper;
    }

    @Override
    public TagDto findTagById(long id) {
        return tagMapper.convertToDto(tagDao.findEntityById(id).orElseThrow(() ->
                new NoSuchEntityException("ex.noSuchEntity", " (id = " + id + ")")));
    }

    @Override
    public Tag findTagByName(String name) {
        return tagDao.findEntityByName(name).orElse(new Tag());
    }

    @Override
    public List<TagDto> findListTags(int pageNumber, int rows) {
        return tagDao.findListEntities(new LinkedMultiValueMap<>(), pageNumber, rows).stream().map(tagMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TagDto createTag(TagDto tagDto) {
        Tag tag = tagMapper.convertToEntity(tagDto);
        if (findTagByName(tag.getName()).getName() != null) {
            throw new DuplicateEntityException("ex.duplicate", tag.getName() + ")");
        }
        return tagMapper.convertToDto(tagDao.createEntity(tag));
    }

    @Override
    @Transactional
    public TagDto updateTag(TagDto tagDto, long id) {
        Tag tag = tagMapper.convertToEntity(tagDto);
        if (findTagByName(tag.getName()).getName() != null) {
            throw new DuplicateEntityException("ex.duplicate", tag.getName() + ")");
        }
        findTagById(id);
        tag.setId(id);
        return tagMapper.convertToDto(tagDao.updateEntity(tag));
    }

    @Override
    @Transactional
    public void deleteTag(long id) {
        Tag tag = tagMapper.convertToEntity(findTagById(id));
        tagDao.deleteGiftCertificateTagByTagId(id);
        tagDao.deleteEntity(tag);
    }

    @Override
    public List<TagDto> findMostWidelyUsedTagsOfCustomersWithHighestCostOfAllOrders(int pageNumber, int rows) {
        return tagDao.findMostWidelyUsedTagsOfCustomersWithHighestCostOfAllOrders(pageNumber, rows).stream().map(tagMapper::convertToDto).collect(Collectors.toList());
    }
}