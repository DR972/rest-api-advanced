package com.epam.esm.hateoas.impl;

import com.epam.esm.controller.TagController;
import com.epam.esm.dto.ListEntitiesDto;
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

    @Override
    public void addLinksForListEntity(ListEntitiesDto<TagDto> tags, int rows, int pageNumber) {
        int numberPages = (int) Math.ceil((float) tags.getTotalNumberObjects() / rows);
        tags.getEntities().forEach(t -> t.add(linkTo(methodOn(TAG_CONTROLLER).getTagById(t.getId())).withRel("getTagById")));
        if (pageNumber < numberPages + 1) {
            tags.add(linkTo(methodOn(TAG_CONTROLLER).getTagById(tags.getEntities().get(0).getId())).withRel("getTagById"));
            tags.add(linkTo(methodOn(TAG_CONTROLLER).createTag(tags.getEntities().get(0))).withRel("createTag"));
            tags.add(linkTo(methodOn(TAG_CONTROLLER).updateTag(tags.getEntities().get(0).getId(), tags.getEntities().get(0))).withRel("updateTag"));
            tags.add(linkTo(methodOn(TAG_CONTROLLER).deleteTag(tags.getEntities().get(0).getId())).withRel("deleteTag"));
        }
        tags.add(linkTo(methodOn(TAG_CONTROLLER).getMostWidelyUsedTagsOfCustomersWithHighestCostOfAllOrders(5, 1))
                .withRel("getMostWidelyUsedTagsOfCustomersWithHighestCostOfAllOrders"));
        tags.add(linkTo(methodOn(TAG_CONTROLLER).getTagList(1, rows)).withRel("getTagList page 1"));
        if (pageNumber > 2 && pageNumber < numberPages + 1) {
            tags.add(linkTo(methodOn(TAG_CONTROLLER).getTagList(pageNumber - 1, rows)).withRel("getTagList page " + (pageNumber - 1)));
        }
        if (pageNumber < numberPages - 1) {
            tags.add(linkTo(methodOn(TAG_CONTROLLER).getTagList(pageNumber + 1, rows)).withRel("getTagList page " + (pageNumber + 1)));
        }
        tags.add(linkTo(methodOn(TAG_CONTROLLER).getTagList(numberPages, rows))
                .withRel("getTagList last page " + numberPages));
    }
}