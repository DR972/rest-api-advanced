package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.hateoas.HateoasAdder;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.util.List;

/**
 * Class {@code TagController} is an endpoint of the API which allows you to perform CRUD operations on tags.
 * Annotated by {@link RestController} without parameters to provide an answer in application/json.
 * Annotated by {@link RequestMapping} with parameter value = "/tags".
 * Annotated by {@link Validated} without parameters  provides checking of constraints in method parameters.
 * So that {@code TagController} is accessed by sending request to /tags.
 *
 * @author Dzmitry Rozmysl
 * @since 1.0
 */
@RestController
@RequestMapping("/tags")
@Validated
public class TagController {
    private static final String ROWS = "rows";
    private static final String PAGE_NUMBER = "pageNumber";
    /**
     * TagService tagService.
     */
    private final TagService tagService;
    private final HateoasAdder<TagDto> hateoasAdder;

    /**
     * The constructor creates a TagController object
     *
     * @param tagService   TagService tagService
     * @param hateoasAdder HateoasAdder<TagDto> hateoasAdder
     */
    @Autowired
    public TagController(TagService tagService, HateoasAdder<TagDto> hateoasAdder) {
        this.tagService = tagService;
        this.hateoasAdder = hateoasAdder;
    }

    /**
     * Method for getting TagDto by ID.
     *
     * @param id TagDto id
     * @return TagDto
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDto getTagById(@PathVariable @Positive(message = "ex.tagIdPositive") long id) {
        TagDto tagDto = tagService.findTagById(id);
        hateoasAdder.addLinks(tagDto);
        return tagDto;
    }


    /**
     * Method for getting list of all TagDto objects.
     *
     * @return list of TagDto objects
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TagDto> getTagList(@RequestParam(name = ROWS, defaultValue = "5") @Positive(message = "ex.rows") int rows,
                                   @RequestParam(name = PAGE_NUMBER, defaultValue = "1") @Positive(message = "ex.page") int pageNumber) {
        List<TagDto> tags = tagService.findListTags((pageNumber - 1) * rows, rows);
        tags.forEach(hateoasAdder::addLinks);
        return tags;
    }

    /**
     * Method for saving new Tag.
     * Annotated by {@link Validated} with parameters TagDto.OnCreate.class provides validation of the fields of the TagDto object when creating.
     *
     * @param tag Tag
     * @return created TagDto
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto createTag(@Validated(TagDto.OnCreate.class) @RequestBody TagDto tag) {
        TagDto tagDto = tagService.createTag(tag);
        hateoasAdder.addLinks(tagDto);
        return tagDto;
    }

    /**
     * Method for updating Tag.
     * Annotated by {@link Validated} with parameters TagDto.OnCreate.class provides validation of the fields of the TagDto object when updating.
     *
     * @param tag new TagDto parameters
     * @param id  TagDto id
     * @return updated TagDto
     */
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDto updateTag(@PathVariable @Positive(message = "ex.tagIdPositive") long id, @Validated(TagDto.OnCreate.class) @RequestBody TagDto tag) {
        TagDto tagDto = tagService.updateTag(tag, id);
        hateoasAdder.addLinks(tagDto);
        return tagDto;
    }

    /**
     * Method for removing Tag by ID.
     *
     * @param id TagDto id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteTag(@PathVariable @Positive(message = "ex.tagIdPositive") long id) {
        tagService.deleteTag(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /**
     * Method for updating Tag.
     * Annotated by {@link Validated} with parameters TagDto.OnCreate.class provides validation of the fields of the TagDto object when updating.
     * <p>
     * //     * @param customer new CustomerDto parameters
     * //     * @param id       CustomerDto id
     *
     * @return updated TagDto
     */

    @GetMapping("/popular")
    @ResponseStatus(HttpStatus.OK)
    public List<TagDto> getMostWidelyUsedTagsOfCustomersWithHighestCostOfAllOrders(@RequestParam(name = ROWS, defaultValue = "5") @Positive(message = "ex.rows") int rows,
                                                                                   @RequestParam(name = PAGE_NUMBER, defaultValue = "1") @Positive(message = "ex.page") int pageNumber) {
        List<TagDto> tags = tagService.findMostWidelyUsedTagsOfCustomersWithHighestCostOfAllOrders((pageNumber - 1) * rows, rows);
        tags.forEach(hateoasAdder::addLinks);
        return tags;
    }
}
