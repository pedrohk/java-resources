package com.pedrohk;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HotelSecurityDeepTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Read operations (GET) should not require CSRF token")
    void getOperationShouldWorkWithoutCsrf() throws Exception {
        mockMvc.perform(get("/api/v1/bookings")
                        .with(user("receptionist")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("Write operations (POST) must be forbidden without CSRF token")
    void postOperationShouldFailWithoutCsrf() throws Exception {
        mockMvc.perform(post("/api/v1/bookings")
                        .with(user("receptionist"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"guestName\": \"Pedro\"}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Write operations (POST) should be successful with valid CSRF token")
    void postOperationShouldSucceedWithCsrf() throws Exception {
        mockMvc.perform(post("/api/v1/bookings")
                        .with(user("receptionist"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"guestName\": \"Pedro\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Success"))
                .andExpect(jsonPath("$.guest").value("Pedro"));
    }

    @Test
    @DisplayName("Unauthorized users should be rejected before CSRF check")
    void unauthorizedShouldBeRejected() throws Exception {
        mockMvc.perform(post("/api/v1/bookings")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"guestName\": \"Hacker\"}"))
                .andExpect(status().isUnauthorized());
    }
}
