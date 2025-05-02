package id.ac.ui.cs.advprog.admin.service.strategy;

import id.ac.ui.cs.advprog.admin.model.Notification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class GlobalNotificationStrategy implements NotificationStrategy {

    @Override
    public Notification createNotification(String title, String message) {
        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setCreatedAt(LocalDateTime.now());
        return notification;
    }
}
