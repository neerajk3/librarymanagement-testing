package com.librarymanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Neerajkumar
 *
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class Authorizationexception extends RuntimeException {
    static final long serialVersionUID = -3387516993334229948L;

    public Authorizationexception(String message) {
        super(message);
    }

    public Authorizationexception(String message, String cause) {
        super(message, new Throwable(cause));
    }

}
