package com.epam.esm.hateoas.impl;

import com.epam.esm.controller.TagController;
import com.epam.esm.dto.TagDto;
import com.epam.esm.hateoas.HateoasAdder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Class {@code TagHateoasAdder} is implementation of interface {@link HateoasAdder}
 * and intended to work with {@link TagDto} objects.
 *
 * @author Dzmitry Rozmysl
 * @since 1.0
 */
@Component
public class TagHateoasAdder implements HateoasAdder<TagDto> {
    private static final Class<TagController> TAG_CONTROLLER = TagController.class;

    @Override
    public void addLinks(TagDto tagDto) {
        tagDto.add(linkTo(methodOn(TAG_CONTROLLER).getTagById(tagDto.getId())).withSelfRel());
        tagDto.add(linkTo(methodOn(TAG_CONTROLLER).getTagList(5, 1)).withRel("getTagList"));
        tagDto.add(linkTo(methodOn(TAG_CONTROLLER).createTag(tagDto)).withRel("createTag"));
        tagDto.add(linkTo(methodOn(TAG_CONTROLLER).updateTag(tagDto.getId(), tagDto)).withRel("updateTag"));
        tagDto.add(linkTo(methodOn(TAG_CONTROLLER).deleteTag(tagDto.getId())).withRel("deleteTag"));
        tagDto.add(linkTo(methodOn(TAG_CONTROLLER).getMostWidelyUsedTagsOfCustomersWithHighestCostOfAllOrders(5, 1))
                .withRel("getMostWidelyUsedTagsOfCustomersWithHighestCostOfAllOrders"));
    }
}