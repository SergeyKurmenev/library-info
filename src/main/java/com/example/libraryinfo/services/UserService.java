package com.example.libraryinfo.services;

import com.example.libraryinfo.entities.User;
import com.example.libraryinfo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public List<User> getRealUsers() {
        return userRepository.getRealUsers();
    }
}
