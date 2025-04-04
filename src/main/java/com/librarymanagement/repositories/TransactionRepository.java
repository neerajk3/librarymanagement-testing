/**
 * 
 */
package com.librarymanagement.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.librarymanagement.entities.Transaction;

/**
 * @author Neerajkumar
 *
 */
public interface TransactionRepository extends MongoRepository<Transaction, String> {

}
