package com.epam.esm.dao.impl;

import com.epam.esm.config.DatabaseTestConfiguration;
import com.epam.esm.dao.TagDao;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DatabaseTestConfiguration.class)
@ActiveProfiles("test")
@Transactional
public class TagDaoImplTest {
    private static final Tag TAG_1 = new Tag(1, "rest");
    private static final Tag TAG_2 = new Tag(2, "nature");
    private static final Tag TAG_3 = new Tag(3, "shopping");
    private static final Tag TAG_4 = new Tag(4, "atv");
    private static final Tag TAG_5 = new Tag(5, "horse");
    private static final Tag TAG_6 = new Tag(6, "theater");
    private static final Tag TAG_7 = new Tag(7, "tool");
    private static final Tag TAG_8 = new Tag(8, "food");
    private static final Tag TAG_9 = new Tag(9, "supermarket");
    private static final Tag TAG_10 = new Tag(10, "restaurant");
    private static final Tag TAG_11 = new Tag(11, "flight");
    private static final Tag TAG_12 = new Tag(12, "visit");
    private static final Tag NEW_TAG = new Tag("new");
    private static final Tag NEW_TAG_5 = new Tag(5, "new horse");

    private final DataSource dataSource;
    private final TagDao tagDao;
    @Value("classpath:schema.sql")
    private Resource schema;

    @Autowired
    public TagDaoImplTest(DataSource dataSource, TagDao tagDao) {
        this.dataSource = dataSource;
        this.tagDao = tagDao;
    }

    @BeforeEach
    public void setUp() {
        ResourceDatabasePopulator tables = new ResourceDatabasePopulator();
        tables.addScript(schema);
        DatabasePopulatorUtils.execute(tables, dataSource);
    }

    @Test
    void findEntityShouldReturnResult() {
        assertEquals(tagDao.findEntityById(1), Optional.of(TAG_1));
        assertEquals(tagDao.findEntityByName(TAG_2.getName()), Optional.of(TAG_2));
    }

    @Test
    void findListEntitiesShouldReturnResult() {
        assertEquals(tagDao.findListEntities(new LinkedMultiValueMap<>(), 0,10),
                Arrays.asList(TAG_1, TAG_2, TAG_3, TAG_4, TAG_5, TAG_6, TAG_7, TAG_8, TAG_9, TAG_10));

        assertEquals(tagDao.findListEntities(new LinkedMultiValueMap<>(), 10,10), Arrays.asList(TAG_11, TAG_12));
    }

    @Test
    void createEntityShouldReturnResult() {
        assertEquals(tagDao.createEntity(NEW_TAG), NEW_TAG);
    }

    @Test
    void updateEntityShouldReturnResult() {
        assertEquals(tagDao.updateEntity(NEW_TAG_5), NEW_TAG_5);
    }

    @Test
    void findMostWidelyUsedTagsOfCustomersWithHighestCostOfAllOrdersShouldReturnResult() {
        assertEquals(tagDao.findMostWidelyUsedTagsOfCustomersWithHighestCostOfAllOrders(0,5), Collections.singletonList(TAG_3));
    }
}
