package com.example.libraryinfo.utils;

import com.example.libraryinfo.entities.User;
import com.example.libraryinfo.repositories.UserRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    @Override
    public void run(String... args) throws Exception {
        populateUsers();
    }

    public void populateUsers() throws IOException, CsvException {
        Resource resource = new ClassPathResource(usersCsvPath);
        CSVReader reader = new CSVReader(new InputStreamReader(resource.getInputStream()));
        Iterator<String[]> rowIterator = reader.readAll().iterator();
        rowIterator.next(); // skip header

        List<User> users = new ArrayList<>();
        while (rowIterator.hasNext()) {
            String[] row = rowIterator.next();
            if (row.length == User.class.getDeclaredFields().length - 1) {
                users.add(Converters.STRING_ARRAY_TO_USER.apply(row));
            }
        }
        userRepository.saveAll(users);
    }
}
