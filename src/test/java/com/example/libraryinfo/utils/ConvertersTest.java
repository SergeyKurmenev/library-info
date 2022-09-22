package com.example.libraryinfo.utils;

import com.example.libraryinfo.entities.Book;
import com.example.libraryinfo.entities.BorrowInfo;
import com.example.libraryinfo.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ConvertersTest {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    @Test
    public void checkStringArrayToUserConverter_allFields() {
        // given: appropriate provided
        String firstName = "firstName";
        String surName = "surName";
        String membershipStart = "01/01/1999";
        String membershipEnd = "02/02/2002";
        String gender = "f";
        String[] row = new String[]{surName, firstName, membershipStart, membershipEnd, gender};
        // when: row applied to converter
        User user = Converters.STRING_ARRAY_TO_USER.apply(row);
        // then: User with all field appears
        assertEquals(firstName, user.getFirstName());
        assertEquals(surName, user.getSurName());
        assertEquals(LocalDate.parse(membershipStart, FORMATTER), user.getMembershipStart());
        assertEquals(LocalDate.parse(membershipEnd, FORMATTER), user.getMembershipEnd());
        assertEquals(gender, user.getGender());
    }

    @Test
    public void checkStringArrayToBookConverter_allFields() {
        // given: appropriate provided
        String title = "title";
        String author = "author";
        String genre = "genre";
        String publisher = "publisher";
        String[] row = new String[]{title, author, genre, publisher};
        // when: row applied to converter
        Book book = Converters.STRING_ARRAY_TO_BOOK.apply(row);
        // then: Book with all field appears
        assertEquals(title, book.getTitle());
        assertEquals(author, book.getAuthor());
        assertEquals(genre, book.getGenre());
        assertEquals(publisher, book.getPublisher());
    }

    @Test
    public void checkStringArrayToBookConverter_emptyTitleLeadToException() {
        // given: appropriate provided
        String title = "";
        String author = "author";
        String genre = "genre";
        String publisher = "publisher";
        String[] row = new String[]{title, author, genre, publisher};
        // when: row applied to converter
        // then: Book with all field appears
        assertThrows(RuntimeException.class, () -> {
            Converters.STRING_ARRAY_TO_BOOK.apply(row);
        });
    }

    @Test
    public void checkStringArrayToBorrowInfoConverter_allFields() {
        // given: appropriate provided
        String borrowerName = "borrowerName";
        String borrowedBook = "borrowedBook";
        String pickupDate = "01/01/1999";
        String returnDate = "02/02/2002";
        String[] row = new String[]{borrowerName, borrowedBook, pickupDate, returnDate};
        // when: row applied to converter
        BorrowInfo info = Converters.STRING_ARRAY_TO_BORROW_INFO.apply(row);
        // then: BorrowedInfo with all field appears
        assertEquals(borrowerName, info.getBorrowerName());
        assertEquals(borrowedBook, info.getBorrowedBook());
        assertEquals(LocalDate.parse(pickupDate, FORMATTER), info.getPickupDate());
        assertEquals(LocalDate.parse(returnDate, FORMATTER), info.getReturnDate());
    }

}
