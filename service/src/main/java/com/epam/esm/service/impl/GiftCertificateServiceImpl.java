package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.GiftCertificate;
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
     * GiftCertificateDtoConverter certificateDtoConverter.
     */
    private final TagService tagService;

    private final GiftCertificateMapper certificateMapper;

    /**
     * The constructor creates a GiftCertificateServiceImpl object
     *
     * @param validator      SortTypeValidator
     * @param certificateDao GiftCertificateDao certificateDao
     *                       //     * @param certificateTagDao       GiftCertificateTagDao certificateTagDao
     * @param tagDao         TagDao tagDao
     * @param dateHandler    DateHandler dateHandler
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


    public GiftCertificateDto findCertificateById(long id) {
        return certificateMapper.convertToDto(certificateDao.findEntityById(id).orElseThrow(() ->
                new NoSuchEntityException("ex.noSuchEntity", " (id = " + id + ")")));
    }

    @Override
    public List<GiftCertificateDto> findListCertificates(MultiValueMap<String, String> params, int pageNumber, int rows) {
        if (params.get(SORTING) != null) {
            validator.validateSortType(params.get(SORTING));
        }
        return certificateDao.findListEntities(params, pageNumber, rows).stream().map(certificateMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public GiftCertificateDto createCertificate(GiftCertificateDto certificateDto) {
        certificateDto.setCreateDate(dateHandler.getCurrentDate());
        certificateDto.setLastUpdateDate(dateHandler.getCurrentDate());

        GiftCertificate certificate = certificateMapper.convertToEntity(certificateDto);
        certificate.setTags(certificateDto.getTags().stream().map(t -> {
            tagDao.createNewTag(t.getName());
            return tagService.findTagByName(t.getName());
        }).collect(Collectors.toList()));
        return certificateMapper.convertToDto(certificateDao.createEntity(certificate));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GiftCertificateDto updateCertificate(GiftCertificateDto certificateDto, long id) {
        GiftCertificate certificate = certificateMapper.convertToEntity(findCertificateById(id));
        certificateMapper.updateGiftCertificateFromDto(certificateDto, certificate);
        certificate.setLastUpdateDate(dateHandler.getCurrentDate());
        certificate.setId(id);
        certificate.setTags(certificateDto.getTags().stream().map(t -> {
            tagDao.createNewTag(t.getName());
            return tagService.findTagByName(t.getName());
        }).collect(Collectors.toList()));
        return certificateMapper.convertToDto(certificateDao.updateEntity(certificate));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCertificate(long id) {
        GiftCertificate certificate = certificateMapper.convertToEntity(findCertificateById(id));
        certificateDao.deleteEntity(certificate);
    }
}