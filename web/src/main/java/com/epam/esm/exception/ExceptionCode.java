package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The  Enum {@code ExceptionCode} presents values which will be set into {@link GlobalExceptionHandler} in case generating exceptions.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@AllArgsConstructor
@Getter
public enum ExceptionCode {
    BAD_REQUEST_EXCEPTION(400, 40001),
    MESSAGE_NOT_READABLE_EXCEPTION(400, 40002),
    ARGUMENT_NOT_VALID(400, 40003),
    DUPLICATE_ENTITY_EXCEPTION(400, 40004),
    SORT_TYPE_EXCEPTION(400, 40005),
    NOT_FOUND_URL_EXCEPTION(404, 40401),
    NOT_FOUND_METHOD_ARGUMENT_EXCEPTION(404, 40402),
    NO_SUCH_ENTITY_EXCEPTION(404, 40403),
    METHOD_NOT_ALLOWED_EXCEPTION(405, 40501),
    CONFLICT_EXCEPTION(409, 40901),
    INTERNAL_SERVER_ERROR_EXCEPTION(500, 50001),
    DATABASE_ERROR(500, 50002);

    /**
     * request status.
     */
    private int status;
    /**
     * error code.
     */
    private int errorCode;
}
