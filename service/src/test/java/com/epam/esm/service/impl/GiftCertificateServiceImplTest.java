package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.ListEntitiesDto;
import com.epam.esm.dto.mapper.GiftCertificateMapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.DateHandler;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.service.TagService;
import com.epam.esm.service.validator.SortTypeValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String TAG = "tag";
    private static final String SORTING = "sorting";
    private static final String LAST_UPDATE_DATE = "-lastUpdateDate";
    private static final String HORSE = " horse";
    private static final String RIDING = "riding";
    private static final String VISIT = "visit";
    private static final String REST = "rest";
    private static final LocalDateTime DATE_TIME = LocalDateTime.parse("2022-05-01T00:00:00.001");

    private static final GiftCertificate GIFT_CERTIFICATE_1 = new GiftCertificate(1, "ATV riding",
            "Description ATV riding", new BigDecimal("100"), 10, LocalDateTime.parse("2022-04-01T10:12:45.123"),
            LocalDateTime.parse("2022-04-07T14:15:13.257"), Arrays.asList(new Tag(1, "rest"), new Tag(2, "nature"), new Tag(4, "atv")));

    private static final GiftCertificate GIFT_CERTIFICATE_2 = new GiftCertificate(2, "Horse riding",
            "Horse riding description", new BigDecimal("80"), 8, LocalDateTime.parse("2022-04-02T10:12:45.123"),
            LocalDateTime.parse("2022-04-05T14:15:13.257"), Arrays.asList(new Tag(1, "rest"), new Tag(2, "nature"), new Tag(5, "horse")));

    private static final GiftCertificate GIFT_CERTIFICATE_3 = new GiftCertificate(3, "Visiting a restaurant",
            "Visiting the Plaza restaurant", new BigDecimal("50"), 7, LocalDateTime.parse("2022-04-02T10:12:45.123"),
            LocalDateTime.parse("2022-04-02T14:15:13.257"), Arrays.asList(new Tag(8, "food"), new Tag(10, "restaurant"), new Tag(12, "visit")));

    private static final GiftCertificate GIFT_CERTIFICATE_4 = new GiftCertificate(4, "Visit to the drama theater",
            "Description visit to the drama theater", new BigDecimal("45"), 2, LocalDateTime.parse("2022-03-30T10:12:45.123"),
            LocalDateTime.parse("2022-04-08T14:15:13.257"), Arrays.asList(new Tag(6, "theater"), new Tag(12, "visit")));

    private static final GiftCertificate GIFT_CERTIFICATE_5 = new GiftCertificate(5, "Shopping at the tool store",
            "Description shopping at the tool store", new BigDecimal("30"), 10, LocalDateTime.parse("2022-03-25T10:12:45.123"),
            LocalDateTime.parse("2022-04-01T14:15:13.257"), Arrays.asList(new Tag(3, "shopping"), new Tag(7, "tool")));

    private static final GiftCertificate GIFT_CERTIFICATE_6 = new GiftCertificate(6, "Shopping at the supermarket",
            "Shopping at Lidl supermarket chain", new BigDecimal("80"), 12, LocalDateTime.parse("2022-04-01T10:12:45.123"),
            LocalDateTime.parse("2022-04-14T14:15:13.257"), Arrays.asList(new Tag(6, "shopping"), new Tag(8, "food"), new Tag(9, "supermarket")));

    private static final GiftCertificate GIFT_CERTIFICATE_7 = new GiftCertificate(7, "Hot air balloon flight",
            "An unforgettable hot air balloon flight", new BigDecimal("150"), 12, LocalDateTime.parse("2022-03-01T10:12:45.123"),
            LocalDateTime.parse("2022-03-14T14:15:13.257"), Arrays.asList(new Tag(1, "rest"), new Tag(2, "nature"), new Tag(11, "flight")));

    private static final GiftCertificate GIFT_CERTIFICATE_8 = new GiftCertificate("new GiftCertificate",
            "new description", new BigDecimal("10"), 10, LocalDateTime.parse("2022-05-01T00:00:00.001"),
            LocalDateTime.parse("2022-05-01T00:00:00.001"), Arrays.asList(new Tag("rest"), new Tag("nature"), new Tag("new")));

    private static final GiftCertificate GIFT_CERTIFICATE_9 = new GiftCertificate(5, null,
            "Description shopping at the tool store", null, 10, null,
            null, Arrays.asList(new Tag("shopping"), new Tag("tool"), new Tag("new")));

    private static final GiftCertificate GIFT_CERTIFICATE_5_NEW = new GiftCertificate(5, "Shopping at the tool store",
            "Description shopping at the tool store", new BigDecimal("30"), 10, LocalDateTime.parse("2022-03-25T10:12:45.123"),
            LocalDateTime.parse("2022-05-01T00:00:00.001"), Arrays.asList(new Tag(3, "shopping"), new Tag(7, "tool"), new Tag("new")));


    private static final GiftCertificateDto GIFT_CERTIFICATE_DTO_1 = new GiftCertificateDto("1", "ATV riding",
            "Description ATV riding", new BigDecimal("100"), 10, LocalDateTime.parse("2022-04-01T10:12:45.123"),
            LocalDateTime.parse("2022-04-07T14:15:13.257"), Arrays.asList(new TagDto("1", "rest"), new TagDto("2", "nature"), new TagDto("4", "atv")));

    private static final GiftCertificateDto GIFT_CERTIFICATE_DTO_2 = new GiftCertificateDto("2", "Horse riding",
            "Horse riding description", new BigDecimal("80"), 8, LocalDateTime.parse("2022-04-02T10:12:45.123"),
            LocalDateTime.parse("2022-04-05T14:15:13.257"), Arrays.asList(new TagDto("1", "rest"), new TagDto("2", "nature"), new TagDto("5", "horse")));

    private static final GiftCertificateDto GIFT_CERTIFICATE_DTO_3 = new GiftCertificateDto("3", "Visiting a restaurant",
            "Visiting the Plaza restaurant", new BigDecimal("50"), 7, LocalDateTime.parse("2022-04-02T10:12:45.123"),
            LocalDateTime.parse("2022-04-02T14:15:13.257"), Arrays.asList(new TagDto("8", "food"), new TagDto("10", "restaurant"), new TagDto("12", "visit")));

    private static final GiftCertificateDto GIFT_CERTIFICATE_DTO_4 = new GiftCertificateDto("4", "Visit to the drama theater",
            "Description visit to the drama theater", new BigDecimal("45"), 2, LocalDateTime.parse("2022-03-30T10:12:45.123"),
            LocalDateTime.parse("2022-04-08T14:15:13.257"), Arrays.asList(new TagDto("6", "theater"), new TagDto("12", "visit")));

    private static final GiftCertificateDto GIFT_CERTIFICATE_DTO_5 = new GiftCertificateDto("5", "Shopping at the tool store",
            "Description shopping at the tool store", new BigDecimal("30"), 10, LocalDateTime.parse("2022-03-25T10:12:45.123"),
            LocalDateTime.parse("2022-04-01T14:15:13.257"), Arrays.asList(new TagDto("3", "shopping"), new TagDto("7", "tool")));

    private static final GiftCertificateDto GIFT_CERTIFICATE_DTO_6 = new GiftCertificateDto("6", "Shopping at the supermarket",
            "Shopping at Lidl supermarket chain", new BigDecimal("80"), 12, LocalDateTime.parse("2022-04-01T10:12:45.123"),
            LocalDateTime.parse("2022-04-14T14:15:13.257"), Arrays.asList(new TagDto("6", "shopping"), new TagDto("8", "food"), new TagDto("9", "supermarket")));

    private static final GiftCertificateDto GIFT_CERTIFICATE_DTO_7 = new GiftCertificateDto("7", "Hot air balloon flight",
            "An unforgettable hot air balloon flight", new BigDecimal("150"), 12, LocalDateTime.parse("2022-03-01T10:12:45.123"),
            LocalDateTime.parse("2022-03-14T14:15:13.257"), Arrays.asList(new TagDto("1", "rest"), new TagDto("2", "nature"), new TagDto("11", "flight")));

    private static final GiftCertificateDto GIFT_CERTIFICATE_DTO_8 = new GiftCertificateDto("new GiftCertificate",
            "new description", new BigDecimal("10"), 10, LocalDateTime.parse("2022-05-01T00:00:00.001"),
            LocalDateTime.parse("2022-05-01T00:00:00.001"), Arrays.asList(new TagDto("rest"), new TagDto("nature"), new TagDto("new")));

    private static final GiftCertificateDto GIFT_CERTIFICATE_DTO_9 = new GiftCertificateDto("5", null,
            "Description shopping at the tool store", null, 10, null,
            null, Arrays.asList(new TagDto("shopping"), new TagDto("tool"), new TagDto("new")));

    private static final GiftCertificateDto GIFT_CERTIFICATE_DTO_5_NEW = new GiftCertificateDto("5", "Shopping at the tool store",
            "Description shopping at the tool store", new BigDecimal("30"), 10, LocalDateTime.parse("2022-03-25T10:12:45.123"),
            LocalDateTime.parse("2022-05-01T00:00:00.001"), Arrays.asList(new TagDto("3", "shopping"), new TagDto("7", "tool"), new TagDto("new")));

    @Mock
    private GiftCertificateDao certificateDao = mock(GiftCertificateDao.class);
    @Mock
    private TagService tagService = mock(TagService.class);
    @Mock
    private SortTypeValidator validator = mock(SortTypeValidator.class);
    @Mock
    private DateHandler dateHandler = mock(DateHandler.class);
    @Mock
    private GiftCertificateMapper certificateMapper = mock(GiftCertificateMapper.class);
    @Mock
    private TagDao tagDao = mock(TagDao.class);
    @InjectMocks
    private GiftCertificateServiceImpl certificateServiceImpl;

    @Test
    void findCertificateByIdShouldReturnResult() {
        when(certificateMapper.convertToDto(GIFT_CERTIFICATE_2)).thenReturn(GIFT_CERTIFICATE_DTO_2);
        when(certificateDao.findEntityById(2L)).thenReturn(Optional.of(GIFT_CERTIFICATE_2));
        certificateServiceImpl.findCertificateById("2");
        verify(certificateDao, times(1)).findEntityById(2L);
        assertEquals(GIFT_CERTIFICATE_DTO_2, certificateServiceImpl.findCertificateById("2"));
    }

    @Test
    void findCertificateByIdShouldThrowException() {
        when(certificateDao.findEntityById(2L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(NoSuchEntityException.class, () -> certificateServiceImpl.findCertificateById("2"));
        assertTrue(exception.getMessage().contains("ex.noSuchEntity"));
    }

    @Test
    void findListCertificatesShouldReturnResult() {
        testFindListCertificatesShouldReturnResult(Arrays.asList(null, null, null, Collections.singletonList(LAST_UPDATE_DATE)),
                Arrays.asList(GIFT_CERTIFICATE_6, GIFT_CERTIFICATE_4, GIFT_CERTIFICATE_1, GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_3, GIFT_CERTIFICATE_5, GIFT_CERTIFICATE_7),
                Arrays.asList(GIFT_CERTIFICATE_DTO_6, GIFT_CERTIFICATE_DTO_4, GIFT_CERTIFICATE_DTO_1, GIFT_CERTIFICATE_DTO_2, GIFT_CERTIFICATE_DTO_3,
                        GIFT_CERTIFICATE_DTO_5, GIFT_CERTIFICATE_DTO_7));

        testFindListCertificatesShouldReturnResult(Arrays.asList(null, Collections.singletonList(VISIT), null, Collections.singletonList(LAST_UPDATE_DATE)),
                Arrays.asList(GIFT_CERTIFICATE_4, GIFT_CERTIFICATE_3), Arrays.asList(GIFT_CERTIFICATE_DTO_4, GIFT_CERTIFICATE_DTO_3));

        testFindListCertificatesShouldReturnResult(Arrays.asList(Collections.singletonList(VISIT), null, null, Collections.singletonList(LAST_UPDATE_DATE)),
                Arrays.asList(GIFT_CERTIFICATE_4, GIFT_CERTIFICATE_3), Arrays.asList(GIFT_CERTIFICATE_DTO_4, GIFT_CERTIFICATE_DTO_3));

        testFindListCertificatesShouldReturnResult(Arrays.asList(null, null, Collections.singletonList(REST), Collections.singletonList(LAST_UPDATE_DATE)),
                Arrays.asList(GIFT_CERTIFICATE_1, GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_7), Arrays.asList(GIFT_CERTIFICATE_DTO_1, GIFT_CERTIFICATE_DTO_2, GIFT_CERTIFICATE_DTO_7));

        testFindListCertificatesShouldReturnResult(Arrays.asList(null, null, Arrays.asList(REST, HORSE), Collections.singletonList(LAST_UPDATE_DATE)),
                Collections.singletonList(GIFT_CERTIFICATE_2), Collections.singletonList(GIFT_CERTIFICATE_DTO_2));

        testFindListCertificatesShouldReturnResult(Arrays.asList(null, Collections.singletonList(RIDING), Collections.singletonList(REST), Arrays.asList(LAST_UPDATE_DATE, NAME)),
                Arrays.asList(GIFT_CERTIFICATE_1, GIFT_CERTIFICATE_2), Arrays.asList(GIFT_CERTIFICATE_DTO_1, GIFT_CERTIFICATE_DTO_2));
    }

    private void testFindListCertificatesShouldReturnResult(List<List<String>> list, List<GiftCertificate> certificates,
                                                            List<GiftCertificateDto> certificateDtos) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        if (list.get(0) != null) {
            params.put(NAME, list.get(0));
        }
        if (list.get(1) != null) {
            params.put(DESCRIPTION, list.get(1));
        }
        if (list.get(2) != null) {
            params.put(TAG, list.get(2));
        }
        if (list.get(3) != null) {
            params.put(SORTING, list.get(3));
        }
        IntStream.range(0, certificates.size()).forEach(i -> when(certificateMapper.convertToDto(certificates.get(i))).thenReturn(certificateDtos.get(i)));
        when(certificateDao.countNumberEntityRows(params)).thenReturn(5L);
        when(certificateDao.findListEntities(params, 0, 5)).thenReturn(certificates);
        assertEquals( new ListEntitiesDto<>(certificateDtos, 1, certificates.size(), 5),
                certificateServiceImpl.findListCertificates(params, 1, 5));
    }

    @Test
    void createCertificateShouldReturnResult() {
        when(dateHandler.getCurrentDate()).thenReturn(DATE_TIME);
        IntStream.range(0, GIFT_CERTIFICATE_8.getTags().size()).forEach(i ->
                when(tagService.findTagByName(GIFT_CERTIFICATE_DTO_8.getTags().get(i).getName())).thenReturn(GIFT_CERTIFICATE_8.getTags().get(i)));
        when(certificateMapper.convertToEntity(GIFT_CERTIFICATE_DTO_8)).thenReturn(GIFT_CERTIFICATE_8);
        when(certificateMapper.convertToDto(GIFT_CERTIFICATE_8)).thenReturn(GIFT_CERTIFICATE_DTO_8);
        when(certificateDao.createEntity(GIFT_CERTIFICATE_8)).thenReturn(GIFT_CERTIFICATE_8);

        certificateServiceImpl.createCertificate(GIFT_CERTIFICATE_DTO_8);
        verify(certificateDao, times(1)).createEntity(GIFT_CERTIFICATE_8);
        assertEquals(GIFT_CERTIFICATE_DTO_8, certificateServiceImpl.createCertificate(GIFT_CERTIFICATE_DTO_8));
    }

    @Test
    void updateCertificateShouldReturnResult() {
        when(certificateMapper.convertToEntity(GIFT_CERTIFICATE_DTO_5)).thenReturn(GIFT_CERTIFICATE_5);
        when(certificateMapper.convertToDto(GIFT_CERTIFICATE_5)).thenReturn(GIFT_CERTIFICATE_DTO_5);
        when(certificateDao.findEntityById(5L)).thenReturn(Optional.of(GIFT_CERTIFICATE_5));
        when(dateHandler.getCurrentDate()).thenReturn(DATE_TIME);

        IntStream.range(0, GIFT_CERTIFICATE_DTO_9.getTags().size()).forEach(i ->
                when(tagService.findTagByName(GIFT_CERTIFICATE_DTO_9.getTags().get(i).getName())).thenReturn(GIFT_CERTIFICATE_9.getTags().get(i)));

        when(certificateMapper.convertToDto(GIFT_CERTIFICATE_5_NEW)).thenReturn(GIFT_CERTIFICATE_DTO_5_NEW);
        when(certificateDao.updateEntity(GIFT_CERTIFICATE_5)).thenReturn(GIFT_CERTIFICATE_5_NEW);
        certificateServiceImpl.updateCertificate(GIFT_CERTIFICATE_DTO_9, "5");
        verify(certificateDao, times(1)).updateEntity(GIFT_CERTIFICATE_5);
        assertEquals(GIFT_CERTIFICATE_DTO_5_NEW, certificateServiceImpl.updateCertificate(GIFT_CERTIFICATE_DTO_9, "5"));
    }

    @Test
    void updateCertificateShouldThrowException() {
        when(certificateDao.findEntityById(2L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(NoSuchEntityException.class, () -> certificateServiceImpl.updateCertificate(GIFT_CERTIFICATE_DTO_5, "2"));
        assertTrue(exception.getMessage().contains("ex.noSuchEntity"));
    }

    @Test
    void deleteCertificateTest() {
        when(certificateMapper.convertToDto(GIFT_CERTIFICATE_3)).thenReturn(GIFT_CERTIFICATE_DTO_3);
        when(certificateMapper.convertToEntity(GIFT_CERTIFICATE_DTO_3)).thenReturn(GIFT_CERTIFICATE_3);
        when(certificateDao.findEntityById(3L)).thenReturn(Optional.of(GIFT_CERTIFICATE_3));
        certificateServiceImpl.deleteCertificate("3");
        verify(certificateDao, times(1)).deleteEntity(GIFT_CERTIFICATE_3);
    }

    @Test
    void deleteCertificateShouldThrowException() {
        when(certificateDao.findEntityById(2L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(NoSuchEntityException.class, () -> certificateServiceImpl.deleteCertificate("2"));
        assertTrue(exception.getMessage().contains("ex.noSuchEntity"));
    }
}
