package com.epam.esm.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The class {@code DeleteTagException} is generated in case entity already exists in database.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 * @see RuntimeException
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DeleteTagException extends RuntimeException {
    /**
     * parameters that caused the DeleteTagException exception.
     */
    private String param;

    /**
     * The constructor creates a DeleteTagException object
     *
     * @param message String message
     * @param param   String param
     */
    public DeleteTagException(String message, String param) {
        super(message);
        this.param = param;
    }
}

