package com.librarymanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Neerajkumar
 *
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    static final long serialVersionUID = -3387516993334229948L;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, String cause) {
        super(message, new Throwable(cause));
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

}
