package id.ac.ui.cs.advprog.admin.service.strategy;
import id.ac.ui.cs.advprog.admin.model.Notification;

public interface NotificationStrategy {
    Notification createNotification(String title, String message);
}