package com.librarymanagement.advices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.librarymanagement.domains.ExceptionResponse;
import com.librarymanagement.exceptions.Authorizationexception;
import com.librarymanagement.exceptions.BadRequestException;
import com.librarymanagement.exceptions.EmptyResponseException;
import com.librarymanagement.exceptions.ForbiddenException;
import com.mongodb.MongoException;

@ControllerAdvice
public class RestControllerAdvice extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestControllerAdvice.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception ex) {
        return genericHandle(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmptyResponseException.class)
    public ResponseEntity<ExceptionResponse> handleEmptyResponseException(final Exception ex) {
        return new ResponseEntity<ExceptionResponse>(getResponses(ex, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestException(final Exception ex) {
        return new ResponseEntity<ExceptionResponse>(getResponses(ex, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ExceptionResponse> handleForbiddenException(final Exception ex) {
        return new ResponseEntity<ExceptionResponse>(getResponses(ex, ex.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Authorizationexception.class)
    public ResponseEntity<ExceptionResponse> handleAuthorizationexception(final Exception ex) {
        LOGGER.error("Exception encountered: {}", ex);
        return new ResponseEntity<ExceptionResponse>(getResponses(ex, ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MongoException.class)
    public ResponseEntity<ExceptionResponse> handleMongoException(Exception ex) {
        LOGGER.error("Error Saving Command to Mongo: {}", ex.getMessage());
        return new ResponseEntity<ExceptionResponse>(getResponses(ex, "Failed to receive Command"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ExceptionResponse> genericHandle(Exception e, HttpStatus status) {
        LOGGER.error("Exception encountered: {}", e);
        return new ResponseEntity<ExceptionResponse>(buildExecutionResponse(e), status);
    }

    private ExceptionResponse buildExecutionResponse(Exception e) {
        ExceptionResponse response = new ExceptionResponse();
        response.setDetailedErrorCode(e.getMessage());
        response.setMessage(e.getCause().getMessage());
        return response;
    }

    private ExceptionResponse getResponses(Exception e, String message) {
        ExceptionResponse response = new ExceptionResponse();
        response.setDetailedErrorCode(message);
        response.setMessage(e.getCause().getMessage());
        return response;
    }

}
