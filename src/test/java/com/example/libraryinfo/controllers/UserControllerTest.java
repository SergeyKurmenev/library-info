package com.example.libraryinfo.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnOnlyUsersWhoHaveBorrowedBooksAlready() throws Exception {
        // when: request for getting users who have borrowed at least one book performed
        mockMvc.perform(get("/api/v1/users/real"))
                .andDo(print())
                .andExpect(status().isOk())
                // then: result list contains only users who borrowed books
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$.*.surName", containsInAnyOrder("Zhungwang", "Odum", "Jumummaaq", "Ghaada", "Barret-Kingsley")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"01/01/1970", "02/02/2077"})
    public void shouldReturnEmptyListIfNoOneUseLibraryAtGivenTime(String date) throws Exception {
        // given: date when no book was borrowed
        // when: request for getting users who borrowed book at that date performed
        mockMvc.perform(post("/api/v1/users/borrowers-on-date")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(date))
                .andDo(print())
                .andExpect(status().isOk())
                // then: empty list returned
                .andExpect(jsonPath("$", empty()));
    }

    @Test
    public void shouldReturnAllUsersWhoBorrowedOnDate() throws Exception {
        // given: date when several books were borrowed
        String borrowedDate = "05/02/2022";
        // when: request for getting users who borrowed book at that date performed
        mockMvc.perform(post("/api/v1/users/borrowers-on-date")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(borrowedDate))
                .andDo(print())
                .andExpect(status().isOk())
                // then: all users who borrowed on this time should be in results
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$.*.surName", containsInAnyOrder("Jumummaaq", "Ghaada", "Odum")));
    }

    @Test
    public void pickupDateShouldContAsBorrowedDate() throws Exception {
        // given: date when only one book was picked up and no one another borrowed
        String borrowedDate = "07/22/2022";
        // when: request for getting users who borrowed book at that date performed
        mockMvc.perform(post("/api/v1/users/borrowers-on-date")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(borrowedDate))
                .andDo(print())
                .andExpect(status().isOk())
                // then: one particular user should appear in results
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].surName", is("Zhungwang")))
                .andExpect(jsonPath("$.[0].firstName", is("Noah")));
    }

    @Test
    public void returnDateShouldContAsBorrowedDate() throws Exception {
        // given: date when only one book was returned up and no one another borrowed
        String borrowedDate = "08/12/2022";
        // when: request for getting users who borrowed book at that date performed
        mockMvc.perform(post("/api/v1/users/borrowers-on-date")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(borrowedDate))
                .andDo(print())
                .andExpect(status().isOk())
                // then: one particular user should appear in results
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].surName", is("Zhungwang")))
                .andExpect(jsonPath("$.[0].firstName", is("Noah")));
    }

}
