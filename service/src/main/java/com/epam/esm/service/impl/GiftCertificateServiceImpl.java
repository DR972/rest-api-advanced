package com.epam.esm.service.impl;

import com.epam.esm.dao.CustomerOrderDao;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.impl.AbstractDao;
import com.epam.esm.dto.ResourceDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.mapper.EntityMapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DeleteEntityException;
import com.epam.esm.service.DateHandler;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.mapper.GiftCertificateMapper;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.SortValueValidator;
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
public class GiftCertificateServiceImpl extends AbstractService<GiftCertificate, Long, GiftCertificateDto> implements GiftCertificateService {

    private static final String SORTING = "sorting";
    /**
     * SortValueValidator validator.
     */
    private final SortValueValidator validator;
    /**
     * GiftCertificateDao certificateDao.
     */
    private final GiftCertificateDao certificateDao;
    /**
     * DateHandler dateHandler.
     */
    private final DateHandler dateHandler;
    /**
     * TagService tagService.
     */
    private final TagService tagService;
    /**
     * TagService tagService.
     */
    private final CustomerOrderDao orderDao;
    /**
     * GiftCertificateMapper certificateMapper.
     */
    private final GiftCertificateMapper certificateMapper;

    /**
     * The constructor creates a GiftCertificateServiceImpl object
     *
     * @param dao               AbstractDao<Tag, Long> dao
     * @param entityMapper      EntityMapper<Tag, TagDto> entityMapper
     * @param validator         SortValueValidator
     * @param certificateDao    GiftCertificateDao certificateDao
     * @param dateHandler       DateHandler dateHandler
     * @param tagService        TagService tagService
     * @param orderDao          CustomerOrderDao orderDao
     * @param certificateMapper GiftCertificateMapper certificateMapper
     */
    @Autowired
    public GiftCertificateServiceImpl(AbstractDao<GiftCertificate, Long> dao, EntityMapper<GiftCertificate, GiftCertificateDto> entityMapper,
                                      SortValueValidator validator, GiftCertificateDao certificateDao, DateHandler dateHandler,
                                      TagService tagService, CustomerOrderDao orderDao, GiftCertificateMapper certificateMapper) {
        super(dao, entityMapper);
        this.validator = validator;
        this.certificateDao = certificateDao;
        this.orderDao = orderDao;
        this.dateHandler = dateHandler;
        this.tagService = tagService;
        this.certificateMapper = certificateMapper;
    }

    @Override
    public ResourceDto<GiftCertificateDto> findListCertificates(MultiValueMap<String, String> params, int pageNumber, int rows) {
        if (params.get(SORTING) != null) {
            validator.validateSortType(params.get(SORTING));
        }
        List<GiftCertificateDto> certificates = certificateDao.findListEntities(params, (pageNumber - 1) * rows, rows)
                .stream().map(entityMapper::convertToDto).collect(Collectors.toList());
        return new ResourceDto<>(certificates, pageNumber, certificates.size(), certificateDao.countNumberEntityRows(params));
    }

    @Override
    @Transactional
    public GiftCertificateDto createCertificate(GiftCertificateDto certificateDto) {
        certificateDto.setCreateDate(dateHandler.getCurrentDate());
        certificateDto.setLastUpdateDate(dateHandler.getCurrentDate());
        GiftCertificate certificate = entityMapper.convertToEntity(certificateDto);
        certificate.setTags(createListTags(certificateDto.getTags()));
        return entityMapper.convertToDto(dao.createEntity(certificate));
    }

    @Override
    @Transactional
    public GiftCertificateDto updateCertificate(GiftCertificateDto certificateDto, String id) {
        GiftCertificate certificate = entityMapper.convertToEntity(findEntityById(id));
        List<Tag> tags = new ArrayList<>(certificate.getTags());
        certificateMapper.updateGiftCertificateFromDto(certificateDto, certificate);

        certificate.setLastUpdateDate(dateHandler.getCurrentDate());
        certificate.setId(Long.valueOf(id));
        certificate.setTags(createListTags(certificateDto.getTags()));

        GiftCertificateDto updatedCertificate = entityMapper.convertToDto(dao.updateEntity(certificate));
        tagService.deleteTagNotAssociatedWithCertificates(tags);
        return updatedCertificate;
    }

    @Override
    @Transactional
    public void deleteCertificate(String id) {
        GiftCertificate certificate = certificateMapper.convertToEntity(findEntityById(id));
        if (orderDao.findCustomerOrderByCertificateId(Long.parseLong(id)).isPresent()) {
            throw new DeleteEntityException("ex.deleteCertificate", id);
        }
        certificateDao.deleteEntity(certificate);
        tagService.deleteTagNotAssociatedWithCertificates(certificate.getTags());
    }

    private List<Tag> createListTags(List<TagDto> tags) {
        return tags.stream().map(t -> tagService.findTagByName(t.getName()).orElse(new Tag(t.getName()))).collect(Collectors.toList());
    }
}