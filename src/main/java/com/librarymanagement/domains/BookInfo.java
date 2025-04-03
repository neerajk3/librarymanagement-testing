/**
 * 
 */
package com.librarymanagement.domains;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.librarymanagement.enums.BookStatus;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Neerajkumar
 *
 */
@Data
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
@ApiModel
public class BookInfo {

    @ApiModelProperty(name = "isbn", dataType = "String", example = "123456789")
    private String isbn;

    @ApiModelProperty(name = "barcode", dataType = "String", example = "000010003")
    private String barcode;

    @ApiModelProperty(name = "title", dataType = "String", example = "JAVA FIRST")
    private String title;

    @ApiModelProperty(name = "subject", dataType = "String", example = "JAVA")
    private String subject;

    @ApiModelProperty(name = "publisher", dataType = "String", example = "Good Books")
    private String publisher;

    @ApiModelProperty(name = "language", dataType = "String", example = "English")
    private String language;

    @ApiModelProperty(name = "numberOfPages", dataType = "int", example = "1000")
    private int numberOfPages;

    @ApiModelProperty(name = "authors", dataType = "List", example = "[\"James Gosling\"]")
    private List<String> authors;

    @ApiModelProperty(name = "price", dataType = "Double", example = "500")
    private double price;

    @ApiModelProperty(name = "dateOfPurchase", dataType = "String", example = "01-11-2020")
    private String dateOfPurchase;

    @ApiModelProperty(name = "publicationDate", dataType = "String", example = "01-10-2020")
    private String publicationDate;

    @ApiModelProperty(name = "placedAt", dataType = "int", example = "0")
    private int placedAt;

    @ApiModelProperty(name = "status", dataType = "BookStatus", example = "AVAILABLE")
    private BookStatus status;

}
