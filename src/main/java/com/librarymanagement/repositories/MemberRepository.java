/**
 * 
 */
package com.librarymanagement.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.librarymanagement.entities.Member;

/**
 * @author Neerajkumar
 *
 */
public interface MemberRepository extends MongoRepository<Member, String> {

}
