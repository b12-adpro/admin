package id.ac.ui.cs.advprog.admin.service.strategy;

import id.ac.ui.cs.advprog.admin.model.Notification;
import id.ac.ui.cs.advprog.admin.model.User;
import id.ac.ui.cs.advprog.admin.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

public class GlobalNotificationStrategy implements NotificationStrategy {
    private final UserRepository userRepository;

    public GlobalNotificationStrategy(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Notification createNotification(String title, String message) {
        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setCreatedAt(LocalDateTime.now());

        List<User> nonAdminUsers = userRepository.findAll().stream()
                .filter(user -> !user.isAdmin())
                .toList();

        notification.setRecipients(nonAdminUsers);
        return notification;
    }
}
