/**
 * 
 */
package com.librarymanagement.entities;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.librarymanagement.constants.LibraryMgmtContant;
import com.librarymanagement.enums.MemberRole;
import com.librarymanagement.enums.MembershipStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Neerajkumar
 *
 */
@Data
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
@Document(LibraryMgmtContant.MEMBER_COLLECTION_NAME)
public class Member {
    @Id
    private ObjectId memberId;

    private char[] password;

    private String contactNumber;

    private String emailId;

    private Address address;

    private LocalDateTime membershipStartTime;

    private LocalDateTime membershipEndTime;

    /*
     * MemberRole can be one of - ADMIN, BORROWER
     */
    private MemberRole role;

    /*
     * Membership status can be one of - ACTIVE,CLOSED,CANCELED,BLACKLISTED,NONE
     */
    private MembershipStatus status;

}
