package com.example.libraryinfo.controllers;

import com.example.libraryinfo.entities.User;
import com.example.libraryinfo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/real")
    public List<User> getRealUsers() {
        return userService.getRealUsers();
    }

}
