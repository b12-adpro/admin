package id.ac.ui.cs.advprog.admin.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllNotifications() throws Exception {
        mockMvc.perform(get("/admin/notifications"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetNotificationById_NotFound() throws Exception {
        mockMvc.perform(get("/admin/notifications/99999999-9999-9999-9999-999999999999")) // UUID not found
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateNotificationSuccess() throws Exception {
        String validNotificationJson = "{ \"title\": \"Important Update\", \"message\": \"Please check the new update.\" }";

        mockMvc.perform(post("/admin/notifications")
                        .contentType("application/json")
                        .content(validNotificationJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Important Update"))
                .andExpect(jsonPath("$.message").value("Please check the new update."));
    }
}
