package com.epam.esm.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The class {@code SortTypeException} is generated if incorrect sorting types are specified.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 * @see RuntimeException
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SortTypeException extends RuntimeException {
    /**
     * parameters that caused the SortTypeException exception.
     */
    private String param;

    /**
     * The constructor creates a SortTypeException object
     *
     * @param message String message
     * @param param   String param
     */
    public SortTypeException(String message, String param) {
        super(message);
        this.param = param;
    }
}