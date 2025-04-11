package id.ac.ui.cs.advprog.admin.controller;

import id.ac.ui.cs.advprog.admin.dto.NotificationDTO;
import id.ac.ui.cs.advprog.admin.model.Notification;
import id.ac.ui.cs.advprog.admin.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

class NotificationControllerTest {

    private MockMvc mockMvc;
    private NotificationService notificationService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        notificationService = mock(NotificationService.class);
        NotificationController controller = new NotificationController(notificationService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testGetNotificationById() throws Exception {
        LocalDateTime now = LocalDateTime.of(2025, 4, 10, 14, 30);
        Notification notif = new Notification();
        notif.setId(1L);
        notif.setTitle("Test Title");
        notif.setMessage("Test Message");
        notif.setCreatedAt(now);

        when(notificationService.getNotificationById(1L)).thenReturn(notif);

        mockMvc.perform(get("/api/admin/notifications/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.message").value("Test Message"));
    }

    @Test
    void testCreateNotification() throws Exception {
        LocalDateTime now = LocalDateTime.of(2025, 4, 11, 12, 0);
        NotificationDTO inputDto = new NotificationDTO();
        inputDto.setTitle("Judul");
        inputDto.setMessage("Isi pesan");
        Notification createdNotif = new Notification();
        createdNotif.setId(1L);
        createdNotif.setTitle("Judul");
        createdNotif.setMessage("Isi pesan");
        createdNotif.setCreatedAt(now);

        when(notificationService.createNotification("Judul", "Isi pesan")).thenReturn(createdNotif);

        mockMvc.perform(post("/api/admin/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Judul"))
                .andExpect(jsonPath("$.message").value("Isi pesan"))
                .andExpect(jsonPath("$.createdAtFormatted").value("11 Apr 2025, 12:00"));
    }

    @Test
    void testGetAllNotifications() throws Exception {
        LocalDateTime time1 = LocalDateTime.of(2025, 4, 11, 8, 0);
        LocalDateTime time2 = LocalDateTime.of(2025, 4, 11, 9, 30);

        Notification notif1 = new Notification();
        notif1.setId(1L);
        notif1.setTitle("Title1");
        notif1.setMessage("Message1");
        notif1.setCreatedAt(time1);

        Notification notif2 = new Notification();
        notif2.setId(2L);
        notif2.setTitle("Title2");
        notif2.setMessage("Message2");
        notif2.setCreatedAt(time2);

        when(notificationService.getAllNotifications()).thenReturn(List.of(notif1, notif2));

        mockMvc.perform(get("/api/admin/notifications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Title1"))
                .andExpect(jsonPath("$[0].createdAtFormatted").value("11 Apr 2025, 08:00"))
                .andExpect(jsonPath("$[1].title").value("Title2"))
                .andExpect(jsonPath("$[1].createdAtFormatted").value("11 Apr 2025, 09:30"));
    }
}
