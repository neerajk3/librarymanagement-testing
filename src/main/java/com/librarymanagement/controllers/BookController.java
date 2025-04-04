/**
 * 
 */
package com.librarymanagement.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.librarymanagement.constants.LibraryMgmtContant;
import com.librarymanagement.domains.BookInfo;
import com.librarymanagement.domains.CheckOutResponse;
import com.librarymanagement.domains.GenericResponse;
import com.librarymanagement.exceptions.Authorizationexception;
import com.librarymanagement.exceptions.BadRequestException;
import com.librarymanagement.exceptions.ForbiddenException;
import com.librarymanagement.services.BookService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author Neerajkumar
 *
 */

@Api(tags = "Book Apis", description = "APIs for book related operation")
@RestController
public class BookController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @ApiOperation(value = "Add Book")
    @PostMapping(value = "/v1/books", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "NTA3ZjE5MWU4MTBjMTk3MjlkZTg2MGVhOnBhc3N3b3Jk")
    public ResponseEntity<List<BookInfo>> addBook(@RequestHeader("Authorization") String token,
            @RequestBody BookInfo bookInfo)
            throws Authorizationexception, ForbiddenException, BadRequestException {

        LOGGER.debug("processing save or update book");

        List<BookInfo> books = bookService.addBook(token, bookInfo);

        return new ResponseEntity<List<BookInfo>>(books,
                HttpStatus.OK);

    }

    @ApiOperation(value = "Delete Book")
    @DeleteMapping(value = "/v1/books/{isbn}/{barcode}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "NTA3ZjE5MWU4MTBjMTk3MjlkZTg2MGVhOnBhc3N3b3Jk"),
            @ApiImplicitParam(name = "isbn", value = "isbn", required = true, allowEmptyValue = false, paramType = "path", dataTypeClass = String.class, example = "123456789"),
            @ApiImplicitParam(name = "barcode", value = "barcode", required = true, allowEmptyValue = false, paramType = "path", dataTypeClass = String.class, example = "000010002")
    })
    public ResponseEntity<GenericResponse> deleteBook(@RequestHeader("Authorization") String token,
            @PathVariable("isbn") String isbn, @PathVariable("barcode") String barcode)
            throws Authorizationexception, ForbiddenException, BadRequestException {
        LOGGER.debug("processing save or update book");

        bookService.deleteBook(token, isbn, barcode);

        return new ResponseEntity<GenericResponse>(new GenericResponse(LibraryMgmtContant.BOOK_DELETE_RESPONSE_SUCCESS_MESSSGE),
                HttpStatus.OK);

    }

    @ApiOperation(value = "Get Book by ISBN")
    @GetMapping(value = "/v1/books/{isbn}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "NTA3ZjE5MWU4MTBjMTk3MjlkZTg2MGVhOnBhc3N3b3Jk"),
            @ApiImplicitParam(name = "isbn", value = "isbn", required = true, allowEmptyValue = false, paramType = "path", dataTypeClass = String.class, example = "123456789")
    })
    public ResponseEntity<List<BookInfo>> getBook(@RequestHeader("Authorization") String token,
            @PathVariable("isbn") String isbn)
            throws Authorizationexception, ForbiddenException, BadRequestException {
        LOGGER.debug("processing retrieve book by isbn:{}", isbn);

        List<BookInfo> books = bookService.getBookByIsbn(token, isbn);

        return new ResponseEntity<List<BookInfo>>(books,
                HttpStatus.OK);

    }

    @ApiOperation(value = "Checkout Book")
    @PostMapping(value = "/v1/books/{isbn}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "NTA3ZjE5MWU4MTBjMTk3MjlkZTg2MGViOnBhc3N3b3Jk"),
            @ApiImplicitParam(name = "isbn", value = "isbn", required = true, allowEmptyValue = false, paramType = "path", dataTypeClass = String.class, example = "123456789")
    })
    public ResponseEntity<CheckOutResponse> checkoutBook(@RequestHeader("Authorization") String token,
            @PathVariable("isbn") String isbn)
            throws Authorizationexception, ForbiddenException, BadRequestException {

        LOGGER.debug("checking out book, isbn:{}", isbn);

        CheckOutResponse status = bookService.checkoutBook(token, isbn);

        return new ResponseEntity<CheckOutResponse>(status,
                HttpStatus.OK);

    }

    // Edit book

    // Search catalog: To search books by title, author, subject or publication
    // date.

    // Renew a book

    // Return a book - return book - to do

}
