package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.AbstractDao;
import com.epam.esm.dto.ResourceDto;
import com.epam.esm.dto.mapper.TagMapper;
import com.epam.esm.entity.Tag;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.DuplicateEntityException;
import com.epam.esm.exception.NoSuchEntityException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@SpringBootTest(classes = TagServiceImpl.class)
public class TagServiceImplTest {
    private static final String REST = "rest";
    private static final String NEW = "new";
    private static final Tag TAG_1 = new Tag(1, "rest");
    private static final Tag TAG_2 = new Tag(2, "nature");
    private static final Tag TAG_3 = new Tag(3, "shopping");
    private static final Tag NEW_TAG = new Tag(0, NEW);
    private static final TagDto TAG_DTO_1 = new TagDto("1", "rest");
    private static final TagDto TAG_DTO_2 = new TagDto("2", "nature");
    private static final TagDto TAG_DTO_3 = new TagDto("3", "shopping");
    private static final TagDto NEW_DTO_TAG = new TagDto("0", NEW);

    @MockBean
    private TagDao tagDao;
    @MockBean
    private TagMapper tagMapper;
    @MockBean
    private AbstractDao<Tag, Long> dao;
    @MockBean
    private GiftCertificateDao certificateDao;
    @Autowired
    private TagServiceImpl tagService;

    @Test
    void findTagByIdShouldReturnResult() {
        when(tagMapper.convertToDto(TAG_2)).thenReturn(TAG_DTO_2);
        when(dao.findEntityById(2L)).thenReturn(Optional.of(TAG_2));
        tagService.findEntityById("2");
        verify(dao, times(1)).findEntityById(2L);
        assertEquals(TAG_DTO_2, tagService.findEntityById("2"));
    }

    @Test
    void findTagByIdShouldThrowException() {
        when(dao.findEntityById(2L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(NoSuchEntityException.class, () -> tagService.findEntityById("2"));
        verify(dao, times(1)).findEntityById(2L);
        assertTrue(exception.getMessage().contains("ex.noSuchEntity"));
    }

    @Test
    void findTagByNameShouldReturnResult() {
        when(dao.findEntityByName(REST)).thenReturn(Optional.of(TAG_1));
        tagService.findTagByName(REST);
        verify(dao, times(1)).findEntityByName(REST);
        assertEquals(TAG_1, tagService.findTagByName(REST));
    }

    @Test
    void findListTagsShouldReturnResult() {
        when(tagMapper.convertToDto(TAG_1)).thenReturn(TAG_DTO_1);
        when(tagMapper.convertToDto(TAG_2)).thenReturn(TAG_DTO_2);
        when(tagMapper.convertToDto(TAG_3)).thenReturn(TAG_DTO_3);
        when(dao.countNumberEntityRows()).thenReturn(5L);
        when(dao.findListEntities(0, 5)).thenReturn(Arrays.asList(TAG_1, TAG_2, TAG_3));
        tagService.findListEntities(1, 5);
        verify(dao, times(1)).findListEntities(0, 5);
        assertEquals(new ResourceDto<>(Arrays.asList(TAG_DTO_1, TAG_DTO_2, TAG_DTO_3), 1, 3, 5),
                tagService.findListEntities(1, 5));
    }

    @Test
    void createTagShouldReturnResult() {
        when(tagMapper.convertToEntity(NEW_DTO_TAG)).thenReturn(NEW_TAG);
        when(tagMapper.convertToDto(NEW_TAG)).thenReturn(NEW_DTO_TAG);
        when(dao.findEntityByName(NEW)).thenReturn(Optional.of(new Tag()));
        when(dao.createEntity(NEW_TAG)).thenReturn(NEW_TAG);
        tagService.createTag(NEW_DTO_TAG);
        verify(dao, times(1)).createEntity(NEW_TAG);
        assertEquals(NEW_DTO_TAG, tagService.createTag(NEW_DTO_TAG));
    }

    @Test
    void createTagShouldThrowException() {
        when(tagMapper.convertToEntity(TAG_DTO_1)).thenReturn(TAG_1);
        when(dao.findEntityByName(REST)).thenReturn(Optional.of(TAG_1));
        Exception exception = assertThrows(DuplicateEntityException.class, () -> tagService.createTag(TAG_DTO_1));
        verify(dao, times(1)).findEntityByName(REST);
        assertTrue(exception.getMessage().contains("ex.duplicate"));
    }

    @Test
    void updateTagShouldReturnResult() {
        when(tagMapper.convertToEntity(NEW_DTO_TAG)).thenReturn(NEW_TAG);
        when(tagMapper.convertToDto(TAG_3)).thenReturn(NEW_DTO_TAG);
        when(dao.findEntityByName(NEW)).thenReturn(Optional.of(new Tag()));
        when(dao.findEntityById(3L)).thenReturn(Optional.of(TAG_3));
        when(dao.updateEntity(NEW_TAG)).thenReturn(TAG_3);
        tagService.updateTag(NEW_DTO_TAG, "3");
        verify(dao, times(1)).updateEntity(NEW_TAG);
        assertEquals(NEW_DTO_TAG, tagService.updateTag(NEW_DTO_TAG, "3"));
    }

    @Test
    void updateTagShouldThrowDuplicateEntityException() {
        when(tagMapper.convertToEntity(TAG_DTO_1)).thenReturn(TAG_1);
        when(dao.findEntityByName(REST)).thenReturn(Optional.of(TAG_1));
        Exception exception = assertThrows(DuplicateEntityException.class, () -> tagService.updateTag(TAG_DTO_1, "2"));
        verify(dao, times(1)).findEntityByName(REST);
        assertTrue(exception.getMessage().contains("ex.duplicate"));
    }

    @Test
    void updateTagShouldThrowNoSuchEntityException() {
        when(tagMapper.convertToEntity(TAG_DTO_1)).thenReturn(TAG_1);
        when(dao.findEntityByName(TAG_1.getName())).thenReturn(Optional.of(new Tag()));
        when(dao.findEntityById(2L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(NoSuchEntityException.class, () -> tagService.updateTag(TAG_DTO_1, "2"));
        verify(dao, times(1)).findEntityById(2L);
        assertTrue(exception.getMessage().contains("ex.noSuchEntity"));
    }

    @Test
    void deleteTagTest() {
        when(tagMapper.convertToDto(TAG_3)).thenReturn(TAG_DTO_3);
        when(tagMapper.convertToEntity(TAG_DTO_3)).thenReturn(TAG_3);
        when(dao.findEntityById(3L)).thenReturn(Optional.of(TAG_3));
        tagService.deleteTag("3");
        verify(dao, times(1)).deleteEntity(TAG_3);
    }

    @Test
    void deleteTagShouldThrowNoSuchEntityException() {
        when(dao.findEntityById(2L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(NoSuchEntityException.class, () -> tagService.deleteTag("2"));
        verify(dao, times(1)).findEntityById(2L);
        assertTrue(exception.getMessage().contains("ex.noSuchEntity"));
    }

    @Test
    void findMostWidelyUsedTagsOfCustomersWithHighestCostOfAllOrdersShouldReturnResult() {
        when(tagMapper.convertToDto(TAG_1)).thenReturn(TAG_DTO_1);
        when(tagMapper.convertToDto(TAG_2)).thenReturn(TAG_DTO_2);
        when(tagDao.countNumberEntityRowsInListOfMostPopularTags()).thenReturn(2L);
        when(tagDao.findMostPopularTag(0, 5)).thenReturn(Arrays.asList(TAG_1, TAG_2));
        tagService.findMostPopularTag(1, 5);
        verify(tagDao, times(1)).findMostPopularTag(0, 5);
        assertEquals(new ResourceDto<>(Arrays.asList(TAG_DTO_1, TAG_DTO_2), 1, 2, 2),
                tagService.findMostPopularTag(1, 5));
    }
}
