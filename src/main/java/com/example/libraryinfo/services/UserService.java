package com.example.libraryinfo.services;

import com.example.libraryinfo.entities.User;
import com.example.libraryinfo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public List<User> getRealUsers() {
        return userRepository.getRealUsers();
    }

    public List<User> getUsersWhoBorrowedOnGivenDate(String date) {
        return userRepository.getUsersWhoBorrowedOnGivenDate(LocalDate.parse(date, FORMATTER));
    }
}
