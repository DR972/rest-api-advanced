package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

/**
 * The class {@code ListEntitiesDto} represents ListEntitiesDto<T>.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListEntitiesDto<T> extends RepresentationModel<ListEntitiesDto<T>> {
    /**
     * List<T> entities
     */
    private List<T> resources;
    /**
     * int pageNumber
     */
    private int pageNumber;
    /**
     * int numberObjects
     */
    private int numberObjects;
    /**
     * long totalNumberObjects
     */
    private long totalNumberObjects;
}
