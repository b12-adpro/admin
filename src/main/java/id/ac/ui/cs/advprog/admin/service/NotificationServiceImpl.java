package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.dto.NotificationDTO;
import id.ac.ui.cs.advprog.admin.model.Notification;
import id.ac.ui.cs.advprog.admin.repository.NotificationRepository;
import id.ac.ui.cs.advprog.admin.service.strategy.NotificationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationStrategy notificationStrategy;
    private final UserService userService;

    @Override
    public NotificationDTO createNotification(String title, String message) {
        Notification notification = notificationStrategy.createNotification(title, message);
        int recipients = userService.countAllUsers();
        notification.setRecipientsCount(recipients);
        userService.getAllUsers().forEach(user ->
                System.out.println("📬 Notification sent to: " + user.getName())
        );
        notificationRepository.save(notification);
        return toDTO(notification);
    }

    @Override
    public List<NotificationDTO> getAllNotifications() {
        return notificationRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public NotificationDTO getNotificationDTOById(UUID id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proof not found"));
        return toDTO(notification);
    }

    private NotificationDTO toDTO(Notification notification) {
        return new NotificationDTO(
                notification.getId(),
                notification.getTitle(),
                notification.getMessage(),
                notification.getCreatedAt(),
                userService.countAllUsers()
        );
    }
}
