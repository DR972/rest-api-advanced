package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.ResourceDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.DateHandler;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.mapper.GiftCertificateMapper;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.service.validator.SortTypeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The class {@code GiftCertificateServiceImpl} is implementation of interface {@link GiftCertificateService}
 * and provides logic to work with {@link com.epam.esm.entity.GiftCertificate}.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private static final String SORTING = "sorting";
    /**
     * SortTypeValidator validator.
     */
    private final SortTypeValidator validator;
    /**
     * GiftCertificateDao certificateDao.
     */
    private final GiftCertificateDao certificateDao;
    /**
     * TagDao tagDao.
     */
    private final TagDao tagDao;
    /**
     * DateHandler dateHandler.
     */
    private final DateHandler dateHandler;
    /**
     * TagService tagService.
     */
    private final TagService tagService;
    /**
     * GiftCertificateMapper certificateMapper.
     */
    private final GiftCertificateMapper certificateMapper;

    /**
     * The constructor creates a GiftCertificateServiceImpl object
     *
     * @param validator         SortTypeValidator
     * @param certificateDao    GiftCertificateDao certificateDao
     * @param tagDao            TagDao tagDao
     * @param dateHandler       DateHandler dateHandler
     * @param tagService        TagService tagService
     * @param certificateMapper GiftCertificateMapper certificateMapper
     */
    @Autowired
    public GiftCertificateServiceImpl(SortTypeValidator validator, GiftCertificateDao certificateDao, TagDao tagDao, DateHandler dateHandler,
                                      TagService tagService, GiftCertificateMapper certificateMapper) {
        this.validator = validator;
        this.certificateDao = certificateDao;
        this.tagDao = tagDao;
        this.dateHandler = dateHandler;
        this.tagService = tagService;
        this.certificateMapper = certificateMapper;
    }

    @Override
    public GiftCertificateDto findCertificateById(String id) {
        return certificateMapper.convertToDto(certificateDao.findEntityById(Long.parseLong(id)).orElseThrow(() ->
                new NoSuchEntityException("ex.noSuchEntity", " (id = " + id + ")")));
    }

    @Override
    public ResourceDto<GiftCertificateDto> findListCertificates(MultiValueMap<String, String> params, int pageNumber, int rows) {
        if (params.get(SORTING) != null) {
            validator.validateSortType(params.get(SORTING));
        }
        List<GiftCertificateDto> certificates = certificateDao.findListEntities(params, (pageNumber - 1) * rows, rows)
                .stream().map(certificateMapper::convertToDto).collect(Collectors.toList());
        return new ResourceDto<>(certificates, pageNumber, certificates.size(), certificateDao.countNumberEntityRows(params));
    }

    @Override
    @Transactional
    public GiftCertificateDto createCertificate(GiftCertificateDto certificateDto) {
        certificateDto.setCreateDate(dateHandler.getCurrentDate());
        certificateDto.setLastUpdateDate(dateHandler.getCurrentDate());
        GiftCertificate certificate = certificateMapper.convertToEntity(certificateDto);
        certificate.setTags(createListTags(certificateDto.getTags()));
        return certificateMapper.convertToDto(certificateDao.createEntity(certificate));
    }

    @Override
    @Transactional
    public GiftCertificateDto updateCertificate(GiftCertificateDto certificateDto, String id) {
        GiftCertificate certificate = certificateMapper.convertToEntity(findCertificateById(id));
        List<Tag> tags = new ArrayList<>(certificate.getTags());
        certificateMapper.updateGiftCertificateFromDto(certificateDto, certificate);

        certificate.setLastUpdateDate(dateHandler.getCurrentDate());
        certificate.setId(Long.valueOf(id));
        certificate.setTags(createListTags(certificateDto.getTags()));

        GiftCertificateDto updatedCertificate = certificateMapper.convertToDto(certificateDao.updateEntity(certificate));
        tagDao.deleteTagNotAssociatedWithCertificates(tags);
        return updatedCertificate;
    }

    @Override
    @Transactional
    public void deleteCertificate(String id) {
        GiftCertificate certificate = certificateMapper.convertToEntity(findCertificateById(id));
        certificateDao.deleteEntity(certificate);
        tagDao.deleteTagNotAssociatedWithCertificates(certificate.getTags());
    }

    private List<Tag> createListTags(List<TagDto> tags) {
        return tags.stream().map(t -> {
            if (tagService.findTagByName(t.getName()).getName() == null) {
                return tagDao.createEntity(new Tag(t.getName()));
            } else {
                return tagService.findTagByName(t.getName());
            }
        }).collect(Collectors.toList());
    }
}