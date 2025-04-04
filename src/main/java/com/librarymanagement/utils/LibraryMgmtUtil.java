/**
 * 
 */
package com.librarymanagement.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.librarymanagement.domains.BookInfo;
import com.librarymanagement.domains.MemberInfo;
import com.librarymanagement.entities.BookCopy;
import com.librarymanagement.entities.Member;
import com.librarymanagement.enums.MemberRole;
import com.librarymanagement.enums.MembershipStatus;
import com.librarymanagement.exceptions.Authorizationexception;

/**
 * @author Neerajkumar
 *
 */
@Service
public class LibraryMgmtUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(LibraryMgmtUtil.class);

    private static final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public Member getMember(MemberInfo memberInfo) {
        Member member = new Member();
        member.setAddress(memberInfo.getAddress());
        member.setContactNumber(memberInfo.getContactNumber());
        member.setEmailId(memberInfo.getEmailId());
        member.setPassword(UUID.randomUUID().toString().substring(0, 6).toCharArray());
        member.setRole(MemberRole.BORROWER);
        member.setStatus(MembershipStatus.INACTIVE);
        member.setMembershipStartTime(LocalDateTime.now());
        member.setMembershipEndTime(LocalDateTime.now());
        return member;

    }

    public MemberInfo getMemberInfo(Member member) {
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setMemberId(member.getMemberId().toString());
        memberInfo.setAddress(member.getAddress());
        memberInfo.setContactNumber(member.getContactNumber());
        memberInfo.setEmailId(member.getEmailId());
        memberInfo.setPassword(new String(member.getPassword()));
        memberInfo.setStatus(member.getStatus());
        memberInfo.setMembershipStartTime(member.getMembershipStartTime());
        memberInfo.setMembershipEndTime(member.getMembershipEndTime());
        return memberInfo;

    }

    public BookCopy getBookCopy(BookInfo bookInfo) {
        BookCopy bookCopy = new BookCopy();
        bookCopy.setIsbn(bookInfo.getIsbn());
        bookCopy.setBarcode(bookInfo.getBarcode());
        bookCopy.setTitle(bookInfo.getTitle());
        bookCopy.setSubject(bookInfo.getSubject());
        bookCopy.setPublisher(bookInfo.getPublisher());
        bookCopy.setLanguage(bookInfo.getLanguage());
        bookCopy.setNumberOfPages(bookInfo.getNumberOfPages());
        bookCopy.setAuthors(bookInfo.getAuthors());
        bookCopy.setPrice(bookInfo.getPrice());
        bookCopy.setDateOfPurchase(LocalDate.parse(bookInfo.getDateOfPurchase(), DATE_PATTERN));
        bookCopy.setPublicationDate(LocalDate.parse(bookInfo.getPublicationDate(), DATE_PATTERN));
        return bookCopy;
    }

    public BookInfo getBookInfo(BookCopy bookCopy) {
        BookInfo bookInfo = new BookInfo();
        bookInfo.setIsbn(bookCopy.getIsbn());
        bookInfo.setBarcode(bookCopy.getBarcode());
        bookInfo.setTitle(bookCopy.getTitle());
        bookInfo.setSubject(bookCopy.getSubject());
        bookInfo.setPublisher(bookCopy.getPublisher());
        bookInfo.setLanguage(bookCopy.getLanguage());
        bookInfo.setNumberOfPages(bookCopy.getNumberOfPages());
        bookInfo.setAuthors(bookCopy.getAuthors());
        bookInfo.setPrice(bookCopy.getPrice());
        bookInfo.setPlacedAt(bookCopy.getPlacedAt());
        bookInfo.setStatus(bookCopy.getCurrentStatus());
        bookInfo.setDateOfPurchase(bookCopy.getDateOfPurchase().toString());
        bookInfo.setPublicationDate(bookCopy.getPublicationDate().toString());
        return bookInfo;
    }

    public String[] decodeToken(String tokenString) {
        byte[] decodedBytes;
        try {
            decodedBytes = Base64.getDecoder().decode(tokenString);
        } catch (Exception e) {
            LOGGER.error("given token is invalid");
            throw new Authorizationexception("INVALID TOKEN", "invalid is token");
        }
        String decodedString = new String(decodedBytes);
        String[] tokenArray = decodedString.split(":");
        return tokenArray;

    }

}
