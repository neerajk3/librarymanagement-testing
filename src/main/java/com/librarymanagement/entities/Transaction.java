package com.librarymanagement.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.librarymanagement.constants.LibraryMgmtContant;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Neerajkumar
 *
 */
@Data
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
@Document(LibraryMgmtContant.TRANSACTION_COLLECTION_NAME)
public class Transaction implements Serializable {

    private static final long serialVersionUID = 3949449035138388264L;

    @Id
    private ObjectId memberId;

    private List<BookTransaction> bookTransactions = new ArrayList<BookTransaction>();

}
