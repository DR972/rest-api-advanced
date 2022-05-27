package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.ResourceDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DeleteTagException;
import com.epam.esm.service.TagService;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.mapper.TagMapper;
import com.epam.esm.exception.DuplicateEntityException;
import com.epam.esm.exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

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
     * GiftCertificateDao certificateDao.
     */
    private final GiftCertificateDao certificateDao;

    /**
     * TagDao tagDao.
     */
    private final TagMapper tagMapper;

    /**
     * The constructor creates a TagServiceImpl object
     *
     * @param tagDao         TagDao tagDao
     * @param certificateDao GiftCertificateDao certificateDao
     * @param tagMapper      TagMapper tagMapper
     */
    @Autowired
    public TagServiceImpl(TagDao tagDao, GiftCertificateDao certificateDao, TagMapper tagMapper) {
        this.tagDao = tagDao;
        this.certificateDao = certificateDao;
        this.tagMapper = tagMapper;
    }

    @Override
    public TagDto findTagById(String id) {
        return tagMapper.convertToDto(tagDao.findEntityById(Long.parseLong(id)).orElseThrow(() ->
                new NoSuchEntityException("ex.noSuchEntity", " (id = " + id + ")")));
    }

    @Override
    public Tag findTagByName(String name) {
        return tagDao.findEntityByName(name).orElse(new Tag());
    }

    @Override
    @Transactional
    public ResourceDto<TagDto> findListTags(int pageNumber, int rows) {
        List<TagDto> tags = tagDao.findListEntities((pageNumber - 1) * rows, rows)
                .stream().map(tagMapper::convertToDto).collect(Collectors.toList());
        return new ResourceDto<>(tags, pageNumber, tags.size(), tagDao.countNumberEntityRows());
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
    public TagDto updateTag(TagDto tagDto, String id) {
        Tag tag = tagMapper.convertToEntity(tagDto);
        if (findTagByName(tag.getName()).getName() != null) {
            throw new DuplicateEntityException("ex.duplicate", tag.getName() + ")");
        }
        findTagById(id);
        tag.setId(Long.valueOf(id));
        return tagMapper.convertToDto(tagDao.updateEntity(tag));
    }

    @Override
    @Transactional
    public void deleteTag(String id) {
        Tag tag = tagMapper.convertToEntity(findTagById(id));
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("tag", tag.getName());
        if (!certificateDao.findListEntities(params, 0, 1).isEmpty()) {
            throw new DeleteTagException("ex.deleteTag", tag.getName() + ")");
        } else tagDao.deleteEntity(tag);
    }

    @Override
    public ResourceDto<TagDto> findMostWidelyUsedTagsOfCustomersWithHighestCostOfAllOrders(int pageNumber, int rows) {
        List<TagDto> tags = tagDao.findMostPopularTag((pageNumber - 1) * rows, rows)
                .stream().map(tagMapper::convertToDto).collect(Collectors.toList());
        return new ResourceDto<>(tags, pageNumber, tags.size(), tagDao.countNumberEntityRowsInListOfMostPopularTags());
    }
}