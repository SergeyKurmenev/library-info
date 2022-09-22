package com.example.libraryinfo.repositories;

import com.example.libraryinfo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getUserByFirstNameAndSurName(String firstName, String surName);

    @Query("select distinct user from User user " +
            "join BorrowInfo info on user.id = info.borrowerId " +
            "where info.id is not null")
    List<User> getRealUsers();

    @Query("select distinct user from User user " +
            "join BorrowInfo info on user.id = info.borrowerId " +
            "where :date between info.pickupDate and info.returnDate")
    List<User> getUsersWhoBorrowedOnGivenDate(LocalDate date);
}
