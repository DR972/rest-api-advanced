package com.epam.esm.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The class {@code NoSuchEntityException} is generated in case entity doesn't found in database.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 * @see RuntimeException
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class NoSuchEntityException extends RuntimeException {
    /**
     * parameters that caused the NoSuchEntityException exception.
     */
    private String param;

    /**
     * The constructor creates a NoSuchEntityException object
     *
     * @param message String message
     * @param param   String param
     */
    public NoSuchEntityException(String message, String param) {
        super(message);
        this.param = param;
    }
}
