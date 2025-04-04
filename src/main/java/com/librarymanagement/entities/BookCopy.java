/**
 * 
 */
package com.librarymanagement.entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.librarymanagement.enums.BookStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Neerajkumar
 *
 */
@Data
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class BookCopy extends Book {

    private static final long serialVersionUID = 1108009779237569186L;

    private BookStatus currentStatus;

    private boolean isReserved;

    private double price;

    private LocalDate dateOfPurchase;

    private LocalDate publicationDate;

}
