package com.librarymanagement.domains;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.librarymanagement.enums.BookStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Neerajkumar
 *
 */
@Data
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
@AllArgsConstructor
public class CheckOutResponse {

    private String isbn;

    private String barcode;

    private LocalDate borrowedOn;

    private LocalDate dueDate;

    private double charge;

    private BookStatus status;

}
