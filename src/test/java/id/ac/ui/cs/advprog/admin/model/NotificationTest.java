package id.ac.ui.cs.advprog.admin.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationTest {

    private Notification notification;
    private User sender;

    @BeforeEach
    void setUp() {
        sender = new User();
        sender.setId(1L);
        sender.setUsername("adminUser");

        notification = new Notification();
        notification.setId(10L);
        notification.setTitle("Pengumuman");
        notification.setMessage("Ada pembaruan sistem.");
        notification.setSender(sender);
        notification.setRecipients(Collections.emptyList());
        notification.setGlobal(true);

        // Simulate the @PrePersist lifecycle hook
        notification.onCreate();
    }

    @Test
    void testNotificationFields() {
        assertEquals(10L, notification.getId());
        assertEquals("Pengumuman", notification.getTitle());
        assertEquals("Ada pembaruan sistem.", notification.getMessage());
        assertEquals(sender, notification.getSender());
        assertTrue(notification.isGlobal());
        assertNotNull(notification.getCreatedAt());
        assertFalse(notification.isRead()); // default value via @PrePersist
    }

    @Test
    void testSetters() {
        notification.setTitle("Update Title");
        notification.setMessage("Pesan baru");
        notification.setRead(true);

        assertEquals("Update Title", notification.getTitle());
        assertEquals("Pesan baru", notification.getMessage());
        assertTrue(notification.isRead());
    }

    @Test
    void testCreatedAt() {
        LocalDateTime now = LocalDateTime.now();
        assertTrue(notification.getCreatedAt().isBefore(now.plusSeconds(1)));
        assertTrue(notification.getCreatedAt().isAfter(now.minusSeconds(5)));
    }
}
