package com.librarymanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Neerajkumar
 *
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EmptyResponseException extends RuntimeException {

    private static final long serialVersionUID = -6856642719277283639L;

    public EmptyResponseException(String message) {
        super(message);
    }

    public EmptyResponseException(String message, String cause) {
        super(message, new Throwable(cause));
    }
}
