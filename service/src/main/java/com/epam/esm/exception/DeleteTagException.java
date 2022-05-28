package com.epam.esm.exception;

import lombok.EqualsAndHashCode;

/**
 * The class {@code DeleteTagException} is generated in case entity already exists in database.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 * @see RuntimeException
 */
@EqualsAndHashCode(callSuper = true)
public class DeleteTagException extends EntityException {

    /**
     * The constructor creates a DeleteTagException object
     *
     * @param message String message
     * @param param   String param
     */
    public DeleteTagException(String message, String param) {
        super(message, param);
    }
}

