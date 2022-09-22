package com.example.libraryinfo.utils;

import com.example.libraryinfo.entities.Book;
import com.example.libraryinfo.entities.BorrowInfo;
import com.example.libraryinfo.entities.User;
import com.example.libraryinfo.repositories.BookRepository;
import com.example.libraryinfo.repositories.BorrowedInfoRepository;
import com.example.libraryinfo.repositories.UserRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
@Component
public class DBPrepareHelper implements CommandLineRunner {
    @Value("${app.provided-resources.users}")
    private String usersCsvPath;
    @Value("${app.provided-resources.books}")
    private String booksCsvPath;
    @Value("${app.provided-resources.borrow-info}")
    private String borrowInfoCsvPath;

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BorrowedInfoRepository borrowedInfoRepository;

    @Override
    public void run(String... args) throws Exception {
        int userAdditionalFieldCount = 1;
        int bookAdditionalFieldCount = 1;
        int borrowInfoAdditionalFieldCount = 2;

        // populate USERS table
        populate(usersCsvPath,
                userRepository,
                Converters.STRING_ARRAY_TO_USER,
                User.class.getDeclaredFields().length - userAdditionalFieldCount);
        // populate BOOKS table
        populate(booksCsvPath,
                bookRepository,
                Converters.STRING_ARRAY_TO_BOOK,
                Book.class.getDeclaredFields().length - bookAdditionalFieldCount);
        // populate BORROWED_INFO table
        populate(borrowInfoCsvPath,
                borrowedInfoRepository,
                Converters.STRING_ARRAY_TO_BORROW_INFO,
                BorrowInfo.class.getDeclaredFields().length - borrowInfoAdditionalFieldCount);
        //match user to borrow-info and add additional foreign key to BORROW_INFO table
        setupBorrowerId();
    }

    private <T> void populate(String csvPath,
                              JpaRepository<T, Long> repository,
                              Function<String[], T> converter,
                              int columNumber)
            throws IOException, CsvException {
        Resource resource = new ClassPathResource(csvPath);
        CSVReader reader = new CSVReader(new InputStreamReader(resource.getInputStream()));
        Iterator<String[]> rowIterator = reader.readAll().iterator();
        rowIterator.next(); // skip header of provided csv

        List<T> entities = new ArrayList<>();
        while (rowIterator.hasNext()) {
            String[] row = rowIterator.next();
            if (row.length == columNumber) {
                try {
                    entities.add(converter.apply(row));
                } catch (RuntimeException e) {
                    // used only for get rid of rows with empty values in source csv
                    // to prevent creation entities with empty fields
                    log.debug("Cannot convert provided row to target entity", e);
                }
            }
        }
        repository.saveAll(entities);
    }

    private void setupBorrowerId() {
        List<BorrowInfo> allInfo = borrowedInfoRepository.findAll();
        allInfo.forEach(info -> {
            String[] nameParts = info.getBorrowerName().split(",");
            userRepository.getUserByFirstNameAndSurName(nameParts[1], nameParts[0])
                    .ifPresent(user -> info.setBorrowerId(user.getId()));
        });
        borrowedInfoRepository.saveAll(allInfo);
    }

}
