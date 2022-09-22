package com.example.libraryinfo.repositories;

import com.example.libraryinfo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getUserByFirstNameAndSurName(String firstName, String surName);
}
