/**
 * 
 */
package com.librarymanagement.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.librarymanagement.entities.Transaction;
import com.librarymanagement.repositories.TransactionRepository;

/**
 * @author Neerajkumar
 *
 */
@Service
public class TransactionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction getBorrowedBookDetailsByMemberId(String memberId) {
        LOGGER.debug("retrieving transaction details for member id:{}", memberId);
        Optional<Transaction> transaction = transactionRepository.findById(memberId);
        return transaction.orElse(null);
    }

    public Transaction saveTransaction(Transaction transaction) {
        Transaction updatedTransaction = transactionRepository.save(transaction);
        return updatedTransaction;
    }

}
