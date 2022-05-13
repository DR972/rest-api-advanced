//package com.epam.esm.dao.impl;
//
//import com.epam.esm.config.DatabaseTestConfiguration;
//import com.epam.esm.dao.TagDao;
//import com.epam.esm.entity.Tag;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(classes = DatabaseTestConfiguration.class)
//@ActiveProfiles("test")
//public class TagDaoImplTest {
//    private static final Tag TAG_1 = new Tag(1, "rest");
//    private static final Tag TAG_2 = new Tag(2, "nature");
//    private static final Tag TAG_3 = new Tag(3, "shopping");
//    private static final Tag TAG_4 = new Tag(4, "atv");
//    private static final Tag TAG_5 = new Tag(5, "horse");
//    private static final Tag TAG_6 = new Tag(6, "theater");
//    private static final Tag TAG_7 = new Tag(7, "tool");
//    private static final Tag TAG_8 = new Tag(8, "food");
//    private static final Tag TAG_9 = new Tag(9, "supermarket");
//    private static final Tag TAG_10 = new Tag(10, "restaurant");
//    private static final Tag TAG_11 = new Tag(11, "flight");
//    private static final Tag TAG_12 = new Tag(12, "visit");
//    private static final Tag NEW_TAG = new Tag("new");
//    @Autowired
//    private  TagDao tagDao;
//    @Autowired
//    private EntityManagerFactory entityManagerFactory;
//    @@PersistenceContext
//    private EntityManager entityManager;

//    public TagDaoImplTest(TagDao tagDao) {
//        this.tagDao = tagDao;
//    }

//    @BeforeEach
//    public void setUp() {
//        ResourceDatabasePopulator tables = new ResourceDatabasePopulator();
//        tables.addScript(db);
//        DatabasePopulatorUtils.execute(tables, dataSource);
//    }



//    @Test
//    @Transactional
//    void findEntity() {
//        assertEquals(tagDao.findEntityById(1), Optional.of(TAG_1));
//        assertEquals(tagDao.findEntityByName(TAG_2.getName()), Optional.of(TAG_2));
//    }

//    @Test
//    void findListEntities() {
//        assertEquals(tagDao.findListEntities(FIND_ALL_TAGS, 1),
//                Arrays.asList(TAG_1, TAG_2, TAG_3, TAG_4, TAG_5, TAG_6, TAG_7, TAG_8, TAG_9, TAG_10, TAG_11, TAG_12));
//
//        assertEquals(tagDao.findListEntities(FIND_ALL_TAGS, 2),
//                Arrays.asList(TAG_4, TAG_11, TAG_8, TAG_5, TAG_2, TAG_1, TAG_10, TAG_3, TAG_9, TAG_6, TAG_7, TAG_12));
//    }
//
//    @Test
//    void createEntity() {
//        assertEquals(tagDao.createEntity(CREATE_TAG, NEW_TAG.getName()), 13);
//
//        assertEquals(tagDao.createEntity(CREATE_TAG, NEW_TAG.getName()), 14);
//    }
//
//    @Test
//    void updateEntity() {
//        tagDao.updateEntity(UPDATE_TAG, NEW, 5);
//        assertEquals(tagDao.findEntity(FIND_TAG_BY_ID, 5), Optional.of(new Tag(5, NEW)));
//
//        certificateTagDao.updateEntity(DELETE_CERTIFICATE_TAG_BY_TAG_ID, 1);
//        tagDao.updateEntity(DELETE_TAG, 1);
//        assertEquals(tagDao.findEntity(FIND_TAG_BY_ID, 1), Optional.empty());
//    }
//
//}
