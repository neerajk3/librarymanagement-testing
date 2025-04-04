package com.librarymanagement;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.librarymanagement.entities.Address;
import com.librarymanagement.entities.Book;
import com.librarymanagement.entities.BookCopy;
import com.librarymanagement.entities.Member;
import com.librarymanagement.enums.BookStatus;
import com.librarymanagement.enums.MemberRole;
import com.librarymanagement.enums.MembershipStatus;
import com.librarymanagement.repositories.BookRepository;
import com.librarymanagement.repositories.MemberRepository;

@SpringBootApplication
@EnableMongoRepositories
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(MemberRepository memberRepository, BookRepository bookRepository) {
        return args -> {

            // Adding librarian
            Address address = new Address();
            address.setAddressLine1("House no. 212");
            address.setAddressLine2("2nd floor");
            address.setArea("HighLake Stree");
            address.setCity("County01");
            address.setState("MI");
            address.setCountry("USA");
            address.setZipCode("000001");
            Member librarian = new Member();
            librarian.setMemberId(new ObjectId("507f191e810c19729de860ea"));
            librarian.setAddress(address);
            librarian.setContactNumber("9191919191");
            librarian.setEmailId("testmail@test.com");
            librarian.setPassword("password".toCharArray());
            librarian.setRole(MemberRole.ADMIN);
            librarian.setStatus(MembershipStatus.ACTIVE);
            librarian.setMembershipStartTime(LocalDateTime.now());
            librarian.setMembershipEndTime(LocalDateTime.now().plusYears(10));
            memberRepository.save(librarian);

            // Adding member
            Member member = new Member();
            member.setMemberId(new ObjectId("507f191e810c19729de860eb"));
            member.setAddress(address);
            member.setContactNumber("9292929292");
            member.setEmailId("testmail01@test.com");
            member.setPassword("password".toCharArray());
            member.setRole(MemberRole.BORROWER);
            member.setStatus(MembershipStatus.INACTIVE);
            member.setMembershipStartTime(LocalDateTime.now());
            member.setMembershipEndTime(LocalDateTime.now().plusYears(10));
            memberRepository.save(member);

            // Books Addition
            Book book = new Book();
            book.setIsbn("123456789");
            book.setTitle("JAVA FIRST");
            book.setSubject("JAVA");
            book.setPublisher("Good Books");
            book.setLanguage("English");
            List<String> authors = new ArrayList<String>();
            Collections.addAll(authors, new String[] { "James Gosling" });
            book.setAuthors(authors);
            book.setPlacedAt(1);

            BookCopy copy01 = new BookCopy();
            copy01.setCurrentStatus(BookStatus.AVAILABLE);
            copy01.setReserved(false);
            copy01.setPrice(1000);
            copy01.setDateOfPurchase(LocalDate.now().plusDays(-30));
            copy01.setPublicationDate(LocalDate.now().plusDays(-90));
            copy01.setIsbn("123456789");
            copy01.setBarcode("0000100001");
            copy01.setTitle("JAVA FIRST");
            copy01.setSubject("JAVA");
            copy01.setLanguage("English");
            copy01.setNumberOfPages(1000);
            copy01.setAuthors(authors);
            copy01.setPlacedAt(1);
            book.getBookCopy().add(copy01);

            BookCopy copy02 = new BookCopy();
            copy02.setCurrentStatus(BookStatus.AVAILABLE);
            copy02.setReserved(false);
            copy02.setPrice(1020);
            copy02.setDateOfPurchase(LocalDate.now().plusDays(-40));
            copy02.setPublicationDate(LocalDate.now().plusDays(-100));
            copy02.setIsbn("123456789");
            copy02.setBarcode("000010002");
            copy02.setTitle("JAVA FIRST");
            copy02.setSubject("JAVA");
            copy02.setLanguage("English");
            copy02.setNumberOfPages(1000);
            copy02.setAuthors(authors);
            copy02.setPlacedAt(1);
            book.getBookCopy().add(copy02);

            bookRepository.save(book);

        };
    }

}
