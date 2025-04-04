/**
 * 
 */
package com.librarymanagement.services;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.librarymanagement.domains.BookInfo;
import com.librarymanagement.domains.CheckOutResponse;
import com.librarymanagement.entities.Book;
import com.librarymanagement.entities.BookCopy;
import com.librarymanagement.entities.BookTransaction;
import com.librarymanagement.entities.Member;
import com.librarymanagement.entities.Transaction;
import com.librarymanagement.enums.BookStatus;
import com.librarymanagement.enums.MemberRole;
import com.librarymanagement.exceptions.Authorizationexception;
import com.librarymanagement.exceptions.BadRequestException;
import com.librarymanagement.exceptions.EmptyResponseException;
import com.librarymanagement.exceptions.ForbiddenException;
import com.librarymanagement.repositories.BookRepository;
import com.librarymanagement.utils.LibraryMgmtUtil;

/**
 * @author Neerajkumar
 *
 */
@Service
public class BookService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemberService.class);

    private AtomicInteger availableRack = new AtomicInteger(2);

    @Autowired
    private BookRepository bookDaoMongoImpl;

    @Autowired
    private MemberService memberService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private LibraryMgmtUtil libraryMgmtUtil;

    public List<BookInfo> addBook(String tokenString, BookInfo bookInfo)
            throws Authorizationexception, ForbiddenException, BadRequestException {
        memberService.verifyToken(tokenString, Arrays.asList(MemberRole.ADMIN));

        BookCopy bookCopy;
        try {
            bookCopy = libraryMgmtUtil.getBookCopy(bookInfo);
            bookCopy.setCurrentStatus(BookStatus.AVAILABLE);
        } catch (Exception e) {
            LOGGER.error("book info provided is in invalid format");
            throw new BadRequestException("INVALID_PAYLOAD", "given book info is in invalid format");
        }
        Book book = bookDaoMongoImpl.findByIsbn(bookInfo.getIsbn());
        if (null != book) {
            if (book.getBookCopy().size() >= 10) {
                LOGGER.error(
                        "No. of copies for ISBN :{} already reached to meximum limit, can add any more book",
                        bookInfo.getIsbn());
                throw new BadRequestException("UPPER_LIMIT_REACHED", "upper limit reached for no. of copies");
            }

        }
        else {
            book = new Book();
            book.setIsbn(bookInfo.getIsbn());
            book.setTitle(bookInfo.getTitle());
            book.setSubject(bookInfo.getSubject());
            book.setPublisher(bookInfo.getPublisher());
            book.setLanguage(bookInfo.getLanguage());
            book.setAuthors(bookInfo.getAuthors());
            book.setPlacedAt(availableRack.getAndIncrement());
        }
        bookCopy.setPlacedAt(book.getPlacedAt());
        book.getBookCopy().add(bookCopy);
        Book updatedBook = bookDaoMongoImpl.save(book);

        LOGGER.info("book with ISBN:{}, barcode:{} has been added successfully", bookInfo.getIsbn(), bookInfo.getBarcode());

        List<BookInfo> bookCopies = updatedBook.getBookCopy().stream()
                .map(copy -> libraryMgmtUtil.getBookInfo(copy))
                .collect(Collectors.toList());

        return bookCopies;
    }

    public void deleteBook(String tokenString, String isbn, String barcode) throws Authorizationexception, ForbiddenException {
        memberService.verifyToken(tokenString, Arrays.asList(MemberRole.ADMIN));
        boolean deleteResult = false;
        Book book = bookDaoMongoImpl.findByIsbn(isbn);
        if (null != book) {
            boolean isAvailable = book.getBookCopy().removeIf(bookCopy -> {
                return (StringUtils.equals(bookCopy.getBarcode(), barcode)
                        && bookCopy.getCurrentStatus() == BookStatus.AVAILABLE);
            });
            if (isAvailable) {
                bookDaoMongoImpl.save(book);
                deleteResult = true;
            }
        }

        if (!deleteResult) {
            LOGGER.error("Book with isbn:{}, barcode:{} is not available", isbn, barcode);
            throw new BadRequestException("BOOK_NOT_AVAILABLE", "book with given details is not available");
        }
    }

    public List<BookInfo> getBookByIsbn(String tokenString, String isbn) {
        memberService.verifyToken(tokenString, Arrays.asList(MemberRole.ADMIN));
        Book book = bookDaoMongoImpl.findByIsbn(isbn);

        if (null == book) {
            LOGGER.error("book not availbale for given isbn :{}", isbn);
            throw new EmptyResponseException("BOOK_NOT_AVAILABLE", "book not availbale for given isbn");
        }
        else {
            List<BookInfo> bookCopies = book.getBookCopy().stream()
                    .map(copy -> libraryMgmtUtil.getBookInfo(copy))
                    .collect(Collectors.toList());
            return bookCopies;
        }

    }

    @SuppressWarnings("unused")
    public CheckOutResponse checkoutBook(String tokenString, String isbn) {
        Member member = memberService.verifyToken(tokenString, Arrays.asList(MemberRole.BORROWER));
        Book book = bookDaoMongoImpl.findByIsbn(isbn);
        BookCopy copy;
        BookStatus status;
        if (null == book) {
            LOGGER.error("book not availbale for given isbn :{}", isbn);
            throw new BadRequestException("BOOK_NOT_AVAILABLE", "book not availbale for given isbn");
        }
        else {
            copy = book.getBookCopy().stream()
                    .filter(bookCopy -> bookCopy.getCurrentStatus() == BookStatus.AVAILABLE)
                    .findAny().orElse(null);
            if (null != copy) {
                copy.setCurrentStatus(BookStatus.CHECKEDOUT);
                status = BookStatus.CHECKEDOUT;
            }
            else {
                copy = book.getBookCopy().stream()
                        .filter(bookCopy -> !bookCopy.isReserved())
                        .findAny().orElse(null);
                if (null != copy) {
                    copy.setReserved(true);
                    status = BookStatus.RESERVED;
                }
                else {
                    LOGGER.error("book not availbale for checkout and reservation for given isbn :{}", isbn);
                    throw new BadRequestException("BOOK_NOT_AVAILABLE", "book not availbale for checkout and reservation");
                }

            }

            Transaction transaction = transactionService.getBorrowedBookDetailsByMemberId(member.getMemberId().toString());

            if (null != transaction && transaction.getBookTransactions().size() >= 10) {
                LOGGER.error("upper limit for borrowing books reached for member id:{}", member.getMemberId());
                throw new BadRequestException("UPPER_LIMIT_REACHED", "upper limit for borrowing books reached for given member");

            }

            if (null == transaction) {
                transaction = new Transaction();
                transaction.setMemberId(member.getMemberId());
            }

            BookTransaction bookTransaction = new BookTransaction();
            bookTransaction.setBarcode(copy.getBarcode());
            bookTransaction.setIsbn(copy.getIsbn());
            bookTransaction.setBorrowedOn(LocalDate.now());
            bookTransaction.setDueDate(LocalDate.now().plusDays(15));
            bookTransaction.setCharge(20);
            bookTransaction.setStatus(status);
            transaction.getBookTransactions().add(bookTransaction);

            Book updatedBook = bookDaoMongoImpl.save(book);
            Transaction updatedTransaction = transactionService.saveTransaction(transaction);

            LOGGER.debug("no.of book checkedout/reserved by member id:{} is :{}", member.getMemberId(),
                    updatedTransaction.getBookTransactions().size());

            CheckOutResponse checkOutResponse = new CheckOutResponse(bookTransaction.getIsbn(), bookTransaction.getBarcode(),
                    bookTransaction.getBorrowedOn(),
                    bookTransaction.getDueDate(), bookTransaction.getCharge(), bookTransaction.getStatus());

            return checkOutResponse;
        }
    }

}
