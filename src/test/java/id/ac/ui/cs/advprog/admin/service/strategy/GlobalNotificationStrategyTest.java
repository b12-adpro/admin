package id.ac.ui.cs.advprog.admin.service.strategy;

import id.ac.ui.cs.advprog.admin.model.Notification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GlobalNotificationStrategyTest {

    @Test
    void testCreateNotification() {
        GlobalNotificationStrategy strategy = new GlobalNotificationStrategy();

        String title = "Pemberitahuan";
        String message = "Sistem akan maintenance malam ini";
        Notification notification = strategy.createNotification(title, message);

        assertNotNull(notification);
        assertEquals(title, notification.getTitle());
        assertEquals(message, notification.getMessage());
        assertNotNull(notification.getCreatedAt());
    }
}
