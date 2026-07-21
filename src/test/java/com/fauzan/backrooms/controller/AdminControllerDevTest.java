package com.fauzan.backrooms.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerDevTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenDevProfile_thenAdminMapIsAccessible() throws Exception {
        mockMvc.perform(get("/admin/map"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-map"));
    }
}