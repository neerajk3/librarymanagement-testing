package com.librarymanagement.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.librarymanagement.entities.Book;

/**
 * @author Neerajkumar
 *
 */
public interface BookRepository extends MongoRepository<Book, String> {

    public Book findByIsbn(String isbn);

}
