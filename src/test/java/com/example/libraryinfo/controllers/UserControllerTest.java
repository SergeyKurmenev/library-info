package com.example.libraryinfo.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        // when: request for getting users who have borrowed at least one book performed
        mockMvc.perform(get("/api/v1/users/real"))
                .andDo(print())
                .andExpect(status().isOk())
                // then: result list contains only users who borrowed books
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$.*.surName", containsInAnyOrder("Zhungwang", "Odum", "Jumummaaq", "Ghaada", "Barret-Kingsley")));
    }
}
