package com.epam.esm.dao.impl;

import com.epam.esm.config.DatabaseTestConfiguration;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DatabaseTestConfiguration.class)
@ActiveProfiles("test")
@Transactional
public class GiftCertificateDaoImplTest {
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String SCRIPT = "script";
    private static final String RIDING = "riding";
    private static final String TAG = "tag";
    private static final String SORTING = "sorting";
    private static final String LAST_UPDATE_DATE = "lastUpdateDate";
    private static final String LAST_UPDATE_DATE_DESC = "-lastUpdateDate";
    private static final String HORSE = "horse";
    private static final String VISIT = "isit";
    private static final String REST = "rest";
    private static final GiftCertificate GIFT_CERTIFICATE_1 = new GiftCertificate(1, "ATV riding", "Description ATV riding",
            new BigDecimal("100.00"), 10, LocalDateTime.parse("2022-04-01T10:12:45.123"), LocalDateTime.parse("2022-04-07T14:15:13.257"),
            Arrays.asList(new Tag(1, "rest"), new Tag(2, "nature"), new Tag(4, "atv")));

    private static final GiftCertificate GIFT_CERTIFICATE_2 = new GiftCertificate(2, "Horse riding", "Horse riding description",
            new BigDecimal("80.00"), 8, LocalDateTime.parse("2022-04-02T10:12:45.123"), LocalDateTime.parse("2022-04-05T14:15:13.257"),
            Arrays.asList(new Tag(1, "rest"), new Tag(2, "nature"), new Tag(5, "horse")));

    private static final GiftCertificate GIFT_CERTIFICATE_3 = new GiftCertificate(3, "Visiting a restaurant", "Visiting the Plaza restaurant",
            new BigDecimal("50.00"), 7, LocalDateTime.parse("2022-04-02T10:12:45.123"), LocalDateTime.parse("2022-04-02T14:15:13.257"),
            Arrays.asList(new Tag(8, "food"), new Tag(10, "restaurant"), new Tag(12, "visit")));

    private static final GiftCertificate GIFT_CERTIFICATE_4 = new GiftCertificate(4, "Visit to the drama theater", "Description visit to the drama theater",
            new BigDecimal("45.00"), 2, LocalDateTime.parse("2022-03-30T10:12:45.123"), LocalDateTime.parse("2022-04-08T14:15:13.257"),
            Arrays.asList(new Tag(6, "theater"), new Tag(12, "visit")));

    private static final GiftCertificate GIFT_CERTIFICATE_5 = new GiftCertificate(5, "Shopping at the tool store", "Description shopping at the tool store",
            new BigDecimal("30.00"), 10, LocalDateTime.parse("2022-03-25T10:12:45.123"), LocalDateTime.parse("2022-04-01T14:15:13.257"),
            Arrays.asList(new Tag(3, "shopping"), new Tag(7, "tool")));

    private static final GiftCertificate GIFT_CERTIFICATE_7 = new GiftCertificate(7, "Hot air balloon flight", "An unforgettable hot air balloon flight",
            new BigDecimal("150.00"), 12, LocalDateTime.parse("2022-03-01T10:12:45.123"), LocalDateTime.parse("2022-03-14T14:15:13.257"),
            Arrays.asList(new Tag(1, "rest"), new Tag(2, "nature"), new Tag(11, "flight")));

    private static final GiftCertificate NEW_GIFT_CERTIFICATE = new GiftCertificate("new GiftCertificate", "new description",
            new BigDecimal("10.00"), 10, Arrays.asList(new Tag("rest"), new Tag("nature"), new Tag("supermarket")));

    private static final GiftCertificate NEW_GIFT_CERTIFICATE_WITH_NEW_TAG = new GiftCertificate("new GiftCertificate", "new description",
            new BigDecimal("10.00"), 10, Arrays.asList(new Tag("rest"), new Tag("nature"), new Tag("new")));

    private static final GiftCertificate UPDATE_GIFT_CERTIFICATE_1 = new GiftCertificate(5, "Shopping", "new Description",
            new BigDecimal("15.00"), 20, null, null,
            Arrays.asList(new Tag(3, "shopping"), new Tag(7, "tool"), new Tag(13, "new")));

    private static final GiftCertificate UPDATE_GIFT_CERTIFICATE_2 = new GiftCertificate(6, "new Shopping", null,
            new BigDecimal("15.00"), 0, null, null,
            Arrays.asList(new Tag(3, "shopping"), new Tag(8, "food"), new Tag(9, "supermarket")));

    private final DataSource dataSource;
    private final GiftCertificateDao certificateDao;
    @Value("classpath:schema.sql")
    private Resource schema;

    @Autowired
    public GiftCertificateDaoImplTest(DataSource dataSource, GiftCertificateDao certificateDao) {
        this.dataSource = dataSource;
        this.certificateDao = certificateDao;
    }

    @BeforeEach
    public void setUp() {
        ResourceDatabasePopulator tables = new ResourceDatabasePopulator();
        tables.addScript(schema);
        DatabasePopulatorUtils.execute(tables, dataSource);
    }

    @Test
    void findEntity() {
        assertEquals(certificateDao.findEntityById(2), Optional.of(GIFT_CERTIFICATE_2));
    }

    @Test
    void findListEntitiesShouldReturnResult() {
        assertEquals(certificateDao.findListEntities(createListParameters(Arrays.asList(null, null, null, Collections.singletonList(LAST_UPDATE_DATE))), 0, 5),
                Arrays.asList(GIFT_CERTIFICATE_7, GIFT_CERTIFICATE_5, GIFT_CERTIFICATE_3, GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_1));

        assertEquals(certificateDao.findListEntities(createListParameters(Arrays.asList(Collections.singletonList(VISIT), Collections.singletonList(VISIT),
                null, Collections.singletonList(LAST_UPDATE_DATE_DESC))), 0, 5), Arrays.asList(GIFT_CERTIFICATE_4, GIFT_CERTIFICATE_3));

        assertEquals(certificateDao.findListEntities(createListParameters(Arrays.asList(null, null, Collections.singletonList(REST),
                Collections.singletonList(NAME))), 0, 5), Arrays.asList(GIFT_CERTIFICATE_1, GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_7));

        assertEquals(certificateDao.findListEntities(createListParameters(Arrays.asList(null, null, Arrays.asList(REST, HORSE),
                Collections.singletonList(NAME))), 0, 5), Collections.singletonList(GIFT_CERTIFICATE_2));

        assertEquals(certificateDao.findListEntities(createListParameters(Arrays.asList(Collections.singletonList(RIDING), Collections.singletonList(SCRIPT),
                Collections.singletonList(HORSE), Collections.singletonList(NAME))), 0, 5), Collections.singletonList(GIFT_CERTIFICATE_2));
    }

    @Test
    void createEntityShouldReturnResult() {
        assertEquals(certificateDao.createEntity(NEW_GIFT_CERTIFICATE), NEW_GIFT_CERTIFICATE);
        assertEquals(certificateDao.createEntity(NEW_GIFT_CERTIFICATE_WITH_NEW_TAG), NEW_GIFT_CERTIFICATE_WITH_NEW_TAG);
    }

    @Test
    void updateEntityShouldReturnResult() {
        assertEquals(certificateDao.updateEntity(UPDATE_GIFT_CERTIFICATE_1), UPDATE_GIFT_CERTIFICATE_1);
        assertEquals(certificateDao.updateEntity(UPDATE_GIFT_CERTIFICATE_2), UPDATE_GIFT_CERTIFICATE_2);
    }

    @Test
    void countNumberEntityRowsShouldReturnResult() {
        assertEquals(certificateDao.countNumberEntityRows(createListParameters(Arrays.asList(null, null, null, Collections.singletonList(LAST_UPDATE_DATE)))), 7);

        assertEquals(certificateDao.countNumberEntityRows(createListParameters(Arrays.asList(Collections.singletonList(VISIT), Collections.singletonList(VISIT),
                null, Collections.singletonList(LAST_UPDATE_DATE_DESC)))), 2);

        assertEquals(certificateDao.countNumberEntityRows(createListParameters(Arrays.asList(null, null, Collections.singletonList(REST),
                Collections.singletonList(NAME)))), 3);

        assertEquals(certificateDao.countNumberEntityRows(createListParameters(Arrays.asList(null, null, Arrays.asList(REST, HORSE),
                Collections.singletonList(NAME)))), 1);

        assertEquals(certificateDao.countNumberEntityRows(createListParameters(Arrays.asList(Collections.singletonList(RIDING), Collections.singletonList(SCRIPT),
                Collections.singletonList(HORSE), Collections.singletonList(NAME)))), 1);
    }


    private MultiValueMap<String, String> createListParameters(List<List<String>> list) {
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
        return params;
    }
}
