package id.ac.ui.cs.advprog.admin.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationTest {

    private Notification notification;

    @BeforeEach
    void setUp() {
        notification = new Notification();
        notification.setId(UUID.randomUUID());
        notification.setTitle("Pengumuman");
        notification.setMessage("Ada pembaruan sistem.");

        // Simulate the @PrePersist lifecycle hook
        notification.onCreate();
    }

    @Test
    void testNotificationFields() {
        assertEquals(notification.getId(), notification.getId());
        assertEquals("Pengumuman", notification.getTitle());
        assertEquals("Ada pembaruan sistem.", notification.getMessage());
        assertNotNull(notification.getCreatedAt());
    }

    @Test
    void testSetters() {
        notification.setTitle("Update Title");
        notification.setMessage("Pesan baru");

        assertEquals("Update Title", notification.getTitle());
        assertEquals("Pesan baru", notification.getMessage());
    }

    @Test
    void testCreatedAt() {
        LocalDateTime now = LocalDateTime.now();
        assertTrue(notification.getCreatedAt().isBefore(now.plusSeconds(1)));
        assertTrue(notification.getCreatedAt().isAfter(now.minusSeconds(5)));
    }
}
