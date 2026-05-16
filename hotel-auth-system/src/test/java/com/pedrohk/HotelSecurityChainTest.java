package com.pedrohk;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HotelSecurityChainTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Public info should be accessible by anyone")
    void publicInfoTest() throws Exception {
        mockMvc.perform(get("/api/public/info"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Guest data should be accessible by Guest Role")
    void guestAccessOwnDataTest() throws Exception {
        mockMvc.perform(get("/api/guest/my-room")
                        .with(httpBasic("guest_user", "password")))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Manager should be able to access Guest data")
    void managerAccessGuestDataTest() throws Exception {
        mockMvc.perform(get("/api/guest/my-room")
                        .with(httpBasic("manager_user", "admin123")))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Guest should be FORBIDDEN from accessing Manager reports")
    void guestCannotAccessManagerDataTest() throws Exception {
        mockMvc.perform(get("/api/manager/reports")
                        .with(httpBasic("guest_user", "password")))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Manager should have access to their own reports")
    void managerAccessOwnDataTest() throws Exception {
        mockMvc.perform(get("/api/manager/reports")
                        .with(httpBasic("manager_user", "admin123")))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Unauthenticated users should be UNAUTHORIZED for secured endpoints")
    void unauthenticatedAccessTest() throws Exception {
        mockMvc.perform(get("/api/guest/my-room"))
                .andExpect(status().isUnauthorized());
    }
}
