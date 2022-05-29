package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.AbstractDao;
import com.epam.esm.dto.ResourceDto;
import com.epam.esm.dto.mapper.EntityMapper;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DeleteTagException;
import com.epam.esm.exception.DuplicateEntityException;
import com.epam.esm.service.TagService;
import com.epam.esm.dto.TagDto;
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
public class TagServiceImpl extends AbstractService<Tag, Long, TagDto> implements TagService {
    /**
     * TagDao tagDao.
     */
    private final TagDao tagDao;

    /**
     * GiftCertificateDao certificateDao.
     */
    private final GiftCertificateDao certificateDao;

    /**
     * The constructor creates a TagServiceImpl object
     *
     * @param dao            AbstractDao<Tag, Long> dao
     * @param entityMapper   EntityMapper<Tag, TagDto> entityMapper
     * @param tagDao         TagDao tagDao
     * @param certificateDao GiftCertificateDao certificateDao
     */
    public TagServiceImpl(AbstractDao<Tag, Long> dao, EntityMapper<Tag, TagDto> entityMapper, TagDao tagDao, GiftCertificateDao certificateDao) {
        super(dao, entityMapper);
        this.tagDao = tagDao;
        this.certificateDao = certificateDao;
    }

    @Override
    public Tag findTagByName(String name) {
        return dao.findEntityByName(name).orElse(new Tag());
    }

    @Override
    @Transactional
    public TagDto createTag(TagDto tagDto) {
        Tag tag = entityMapper.convertToEntity(tagDto);
        if (findTagByName(tag.getName()).getName() != null) {
            throw new DuplicateEntityException("ex.duplicate", tag.getName());
        }
        return entityMapper.convertToDto(dao.createEntity(tag));
    }

    @Override
    @Transactional
    public TagDto updateTag(TagDto tagDto, String id) {
        Tag tag = entityMapper.convertToEntity(tagDto);
        System.out.println(tag);
        if (findTagByName(tag.getName()).getName() != null) {
            throw new DuplicateEntityException("ex.duplicate", tag.getName());
        }
        findEntityById(id);
        tag.setId(Long.valueOf(id));
        return entityMapper.convertToDto(dao.updateEntity(tag));
    }

    @Override
    @Transactional
    public void deleteTag(String id) {
        Tag tag = entityMapper.convertToEntity(findEntityById(id));
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("tag", tag.getName());
        if (!certificateDao.findListEntities(params, 0, 1).isEmpty()) {
            throw new DeleteTagException("ex.deleteTag", tag.getName());
        } else dao.deleteEntity(tag);
    }

    @Override
    public ResourceDto<TagDto> findMostPopularTag(int pageNumber, int rows) {
        List<TagDto> tags = tagDao.findMostPopularTag((pageNumber - 1) * rows, rows)
                .stream().map(entityMapper::convertToDto).collect(Collectors.toList());
        return new ResourceDto<>(tags, pageNumber, tags.size(), tagDao.countNumberEntityRowsInListOfMostPopularTags());
    }
}