package com.librarymanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Neerajkumar
 *
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {
    static final long serialVersionUID = -3387516993334229948L;

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, String cause) {
        super(message, new Throwable(cause));
    }

}
