//package com.epam.esm.dao.impl;
//
////import com.epam.esm.config.DatabaseTestConfiguration;
////import com.epam.esm.dao.GiftCertificateDao;
////import com.epam.esm.dao.GiftCertificateTagDao;
////import com.epam.esm.dao.TagDao;
//import com.epam.esm.entity.GiftCertificate;
//import com.epam.esm.entity.Tag;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.Resource;
//import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
//import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import javax.sql.DataSource;
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.Optional;
//
//import static com.epam.esm.dao.util.SqlQueryTest.FIND_ALL_CERTIFICATES_ORDER_BY_DATE_ASC;
//import static com.epam.esm.dao.util.SqlQueryTest.FIND_CERTIFICATE_BY_ID;
//import static com.epam.esm.dao.util.SqlQueryTest.FIND_ALL_CERTIFICATES_BY_PART_NAME_OR_DESCRIPTION_ORDER_BY_DATE_DESC;
//import static com.epam.esm.dao.util.SqlQueryTest.VISIT;
//import static com.epam.esm.dao.util.SqlQueryTest.FIND_ALL_CERTIFICATES_WITH_SPECIFIC_TAG_ORDER_BY_NAME;
//import static com.epam.esm.dao.util.SqlQueryTest.REST;
//import static com.epam.esm.dao.util.SqlQueryTest.FIND_ALL_CERTIFICATES_BY_PART_NAME_OR_DESCRIPTION_WITH_SPECIFIC_TAG_ORDER_BY_DATE_DESC_AND_NAME;
//import static com.epam.esm.dao.util.SqlQueryTest.SCRIPT;
//import static com.epam.esm.dao.util.SqlQueryTest.NATURE;
//import static com.epam.esm.dao.util.SqlQueryTest.CREATE_CERTIFICATE;
//import static com.epam.esm.dao.util.SqlQueryTest.UPDATE_ALL_CERTIFICATE_FIELDS;
//import static com.epam.esm.dao.util.SqlQueryTest.CREATE_TAG;
//import static com.epam.esm.dao.util.SqlQueryTest.NEW;
//import static com.epam.esm.dao.util.SqlQueryTest.CREATE_CERTIFICATE_TAG_BY_TAG_ID;
//import static com.epam.esm.dao.util.SqlQueryTest.UPDATE_CERTIFICATE_NAME_PRICE;
//import static com.epam.esm.dao.util.SqlQueryTest.DELETE_CERTIFICATE_TAG_BY_CERTIFICATE_ID;
//import static com.epam.esm.dao.util.SqlQueryTest.DELETE_CERTIFICATE;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = DatabaseTestConfiguration.class)
//@ActiveProfiles("test")
//public class GiftCertificateDaoImplTest {
//    private static final GiftCertificate GIFT_CERTIFICATE_1 = new GiftCertificate(1, "ATV riding", "Description ATV riding",
//            new BigDecimal("100.00"), 10, LocalDateTime.parse("2022-04-01T10:12:45.123"), LocalDateTime.parse("2022-04-07T14:15:13.257"),
//            Arrays.asList(new Tag(1, "rest"), new Tag(2, "nature"), new Tag(4, "atv")));
//
//    private static final GiftCertificate GIFT_CERTIFICATE_2 = new GiftCertificate(2, "Horse riding", "Horse riding description",
//            new BigDecimal("80.00"), 8, LocalDateTime.parse("2022-04-02T10:12:45.123"), LocalDateTime.parse("2022-04-05T14:15:13.257"),
//            Arrays.asList(new Tag(1, "rest"), new Tag(2, "nature"), new Tag(5, "horse")));
//
//    private static final GiftCertificate GIFT_CERTIFICATE_3 = new GiftCertificate(3, "Visiting a restaurant", "Visiting the Plaza restaurant",
//            new BigDecimal("50.00"), 7, LocalDateTime.parse("2022-04-02T10:12:45.123"), LocalDateTime.parse("2022-04-02T14:15:13.257"),
//            Arrays.asList(new Tag(8, "food"), new Tag(10, "restaurant"), new Tag(12, "visit")));
//
//    private static final GiftCertificate GIFT_CERTIFICATE_4 = new GiftCertificate(4, "Visit to the drama theater", "Description visit to the drama theater",
//            new BigDecimal("45.00"), 2, LocalDateTime.parse("2022-03-30T10:12:45.123"), LocalDateTime.parse("2022-04-08T14:15:13.257"),
//            Arrays.asList(new Tag(6, "theater"), new Tag(12, "visit")));
//
//    private static final GiftCertificate GIFT_CERTIFICATE_5 = new GiftCertificate(5, "Shopping at the tool store", "Description shopping at the tool store",
//            new BigDecimal("30.00"), 10, LocalDateTime.parse("2022-03-25T10:12:45.123"), LocalDateTime.parse("2022-04-01T14:15:13.257"),
//            Arrays.asList(new Tag(3, "shopping"), new Tag(7, "tool")));
//
//    private static final GiftCertificate GIFT_CERTIFICATE_6 = new GiftCertificate(6, "Shopping at the supermarket", "Shopping at Lidl supermarket chain",
//            new BigDecimal("80.00"), 12, LocalDateTime.parse("2022-04-01T10:12:45.123"), LocalDateTime.parse("2022-04-14T14:15:13.257"),
//            Arrays.asList(new Tag(3, "shopping"), new Tag(8, "food"), new Tag(9, "supermarket")));
//
//    private static final GiftCertificate GIFT_CERTIFICATE_7 = new GiftCertificate(7, "Hot air balloon flight", "An unforgettable hot air balloon flight",
//            new BigDecimal("150.00"), 12, LocalDateTime.parse("2022-03-01T10:12:45.123"), LocalDateTime.parse("2022-03-14T14:15:13.257"),
//            Arrays.asList(new Tag(1, "rest"), new Tag(2, "nature"), new Tag(11, "flight")));
//
//    private static final GiftCertificate NEW_GIFT_CERTIFICATE = new GiftCertificate("new GiftCertificate", "new description",
//            new BigDecimal("10.00"), 10, Arrays.asList(new Tag("rest"), new Tag("nature"), new Tag("new")));
//
//    private static final GiftCertificate UPDATE_GIFT_CERTIFICATE_1 = new GiftCertificate(5, "Shopping", "new Description",
//            new BigDecimal("15.00"), 20, null, null,
//            Arrays.asList(new Tag(3, "shopping"), new Tag(7, "tool"), new Tag(13, "new")));
//
//    private static final GiftCertificate UPDATE_GIFT_CERTIFICATE_2 = new GiftCertificate(6, "new Shopping", null,
//            new BigDecimal("15.00"), 0, null, null,
//            Arrays.asList(new Tag(3, "shopping"), new Tag(8, "food"), new Tag(9, "supermarket")));
//
//    private final DataSource dataSource;
//    private final GiftCertificateDao certificateDao;
//    private final TagDao tagDao;
//    private final GiftCertificateTagDao certificateTagDao;
//    @Value("classpath:database/schema.sql")
//    private Resource db;
//
//    @Autowired
//    public GiftCertificateDaoImplTest(DataSource dataSource, GiftCertificateDao certificateDao, TagDao tagDao, GiftCertificateTagDao certificateTagDao) {
//        this.dataSource = dataSource;
//        this.certificateDao = certificateDao;
//        this.tagDao = tagDao;
//        this.certificateTagDao = certificateTagDao;
//    }
//
//    @BeforeEach
//    public void setUp() {
//        ResourceDatabasePopulator tables = new ResourceDatabasePopulator();
//        tables.addScript(db);
//        DatabasePopulatorUtils.execute(tables, dataSource);
//    }
//
//    @Test
//    void findEntity() {
//        assertEquals(certificateDao.findEntity(FIND_CERTIFICATE_BY_ID, 2L), Optional.of(GIFT_CERTIFICATE_2));
//    }
//
//    @Test
//    void findListEntities() {
//        assertEquals(certificateDao.findListEntities(FIND_ALL_CERTIFICATES_ORDER_BY_DATE_ASC, 7, 5, 0, 5),
//                Arrays.asList(GIFT_CERTIFICATE_7, GIFT_CERTIFICATE_5, GIFT_CERTIFICATE_3, GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_1));
//
//        assertEquals(certificateDao.findListEntities(FIND_ALL_CERTIFICATES_BY_PART_NAME_OR_DESCRIPTION_ORDER_BY_DATE_DESC, VISIT, VISIT, -7, 5, 0, 5),
//                Arrays.asList(GIFT_CERTIFICATE_4, GIFT_CERTIFICATE_3));
//
//        assertEquals(certificateDao.findListEntities(FIND_ALL_CERTIFICATES_WITH_SPECIFIC_TAG_ORDER_BY_NAME, REST, 2, 5, 0, 5),
//                Arrays.asList(GIFT_CERTIFICATE_1, GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_7));
//
//        assertEquals(certificateDao.findListEntities(FIND_ALL_CERTIFICATES_BY_PART_NAME_OR_DESCRIPTION_WITH_SPECIFIC_TAG_ORDER_BY_DATE_DESC_AND_NAME,
//                        SCRIPT, SCRIPT, NATURE, -7, 2, 5, 0, 5),
//                Arrays.asList(GIFT_CERTIFICATE_1, GIFT_CERTIFICATE_2));
//    }
//
//    @Test
//    void createEntity() {
//        assertEquals(certificateDao.createEntity(CREATE_CERTIFICATE,
//                NEW_GIFT_CERTIFICATE.getName(),
//                NEW_GIFT_CERTIFICATE.getDescription(),
//                NEW_GIFT_CERTIFICATE.getPrice(),
//                NEW_GIFT_CERTIFICATE.getDuration(),
//                LocalDateTime.now(),
//                LocalDateTime.now()), 8);
//
//        assertEquals(certificateDao.createEntity(CREATE_CERTIFICATE,
//                NEW_GIFT_CERTIFICATE.getName(),
//                NEW_GIFT_CERTIFICATE.getDescription(),
//                NEW_GIFT_CERTIFICATE.getPrice(),
//                NEW_GIFT_CERTIFICATE.getDuration(),
//                LocalDateTime.now(),
//                LocalDateTime.now()), 9);
//
//    }
//
//    @Test
//    void updateEntity() {
//        LocalDateTime dateTime = LocalDateTime.now();
//        certificateDao.updateEntity(UPDATE_ALL_CERTIFICATE_FIELDS,
//                UPDATE_GIFT_CERTIFICATE_1.getName(),
//                UPDATE_GIFT_CERTIFICATE_1.getDescription(),
//                UPDATE_GIFT_CERTIFICATE_1.getPrice(),
//                UPDATE_GIFT_CERTIFICATE_1.getDuration(),
//                dateTime, 5);
//        long tagId = tagDao.createEntity(CREATE_TAG, NEW);
//        certificateTagDao.updateEntity(CREATE_CERTIFICATE_TAG_BY_TAG_ID, UPDATE_GIFT_CERTIFICATE_1.getId(), tagId);
//        assertEquals(certificateDao.findEntity(FIND_CERTIFICATE_BY_ID, UPDATE_GIFT_CERTIFICATE_1.getId()),
//                Optional.of(new GiftCertificate(UPDATE_GIFT_CERTIFICATE_1.getId(),
//                        UPDATE_GIFT_CERTIFICATE_1.getName(),
//                        UPDATE_GIFT_CERTIFICATE_1.getDescription(),
//                        UPDATE_GIFT_CERTIFICATE_1.getPrice(),
//                        UPDATE_GIFT_CERTIFICATE_1.getDuration(),
//                        GIFT_CERTIFICATE_5.getCreateDate(),
//                        dateTime,
//                        UPDATE_GIFT_CERTIFICATE_1.getTags())));
//
//        certificateDao.updateEntity(UPDATE_CERTIFICATE_NAME_PRICE,
//                UPDATE_GIFT_CERTIFICATE_2.getName(),
//                UPDATE_GIFT_CERTIFICATE_2.getPrice(),
//                dateTime, 6);
//        assertEquals(certificateDao.findEntity(FIND_CERTIFICATE_BY_ID, UPDATE_GIFT_CERTIFICATE_2.getId()),
//                Optional.of(new GiftCertificate(UPDATE_GIFT_CERTIFICATE_2.getId(),
//                        UPDATE_GIFT_CERTIFICATE_2.getName(),
//                        GIFT_CERTIFICATE_6.getDescription(),
//                        UPDATE_GIFT_CERTIFICATE_2.getPrice(),
//                        GIFT_CERTIFICATE_6.getDuration(),
//                        GIFT_CERTIFICATE_6.getCreateDate(),
//                        dateTime,
//                        UPDATE_GIFT_CERTIFICATE_2.getTags())));
//
//        certificateTagDao.updateEntity(DELETE_CERTIFICATE_TAG_BY_CERTIFICATE_ID, 1);
//        certificateDao.updateEntity(DELETE_CERTIFICATE, 1);
//        assertEquals(certificateDao.findEntity(FIND_CERTIFICATE_BY_ID, 1), Optional.empty());
//    }
//}
