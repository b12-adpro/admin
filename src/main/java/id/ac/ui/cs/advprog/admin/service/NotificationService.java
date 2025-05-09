package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.dto.NotificationDTO;
import java.util.List;
import java.util.UUID;

public interface NotificationService {
    NotificationDTO createNotification(String title, String message);
    List<NotificationDTO> getAllNotifications();
    NotificationDTO getNotificationDTOById(UUID id);
}
