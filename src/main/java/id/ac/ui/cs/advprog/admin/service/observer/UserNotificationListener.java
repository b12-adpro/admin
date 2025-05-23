package id.ac.ui.cs.advprog.admin.service.observer;

import id.ac.ui.cs.advprog.admin.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserNotificationListener implements NotificationListener {

    private final UserService userService;

    public UserNotificationListener(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onNotification(String message) {
        userService.getAllActiveUsers().forEach(user -> {
            System.out.println("📬 Sending notification to " + user.getName() + ": " + message);
        });
    }
}
