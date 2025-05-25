package id.ac.ui.cs.advprog.admin.controller;

import id.ac.ui.cs.advprog.admin.dto.NotificationDTO;
import id.ac.ui.cs.advprog.admin.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(notificationController)
                .build();
    }

    @Test
    void testGetAllNotifications() throws Exception {
        NotificationDTO notification1 = new NotificationDTO(
                UUID.randomUUID(), "Title 1", "Message 1", LocalDateTime.now(), 10
        );
        NotificationDTO notification2 = new NotificationDTO(
                UUID.randomUUID(), "Title 2", "Message 2", LocalDateTime.now(), 20
        );

        when(notificationService.getAllNotifications())
                .thenReturn(List.of(notification1, notification2));

        mockMvc.perform(get("/admin/notifications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Title 1"))
                .andExpect(jsonPath("$[1].title").value("Title 2"));
    }

    @Test
    void testCreateNotificationSuccess() throws Exception {
        NotificationDTO savedNotification = new NotificationDTO(
                UUID.randomUUID(),
                "Important Update",
                "Please check the new update.",
                LocalDateTime.now(),
                100
        );

        when(notificationService.createNotification(anyString(), anyString()))
                .thenReturn(savedNotification);

        mockMvc.perform(post("/admin/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Important Update\",\"message\":\"Please check the new update.\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Important Update"))
                .andExpect(jsonPath("$.message").value("Please check the new update."));
    }

    @Test
    void testGetNotificationById() throws Exception {
        // Create test notification
        UUID notificationId = UUID.randomUUID();
        NotificationDTO notification = new NotificationDTO(
                notificationId,
                "Notification Title",
                "Notification Message",
                LocalDateTime.now(),
                5
        );

        // Mock service method
        when(notificationService.getNotificationDTOById(notificationId))
                .thenReturn(notification);

        // Perform test
        mockMvc.perform(get("/admin/notifications/{id}", notificationId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(notificationId.toString()))
                .andExpect(jsonPath("$.title").value("Notification Title"))
                .andExpect(jsonPath("$.message").value("Notification Message"));
    }
}