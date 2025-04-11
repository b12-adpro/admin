package id.ac.ui.cs.advprog.admin.controller;

import id.ac.ui.cs.advprog.admin.model.Notification;
import id.ac.ui.cs.advprog.admin.dto.NotificationDTO;
import id.ac.ui.cs.advprog.admin.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
        Notification notification = notificationService.getNotificationById(id);
        return ResponseEntity.ok(notification);
    }

    @PostMapping
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody NotificationDTO dto) {
        Notification created = notificationService.createNotification(dto.getTitle(), dto.getMessage());
        NotificationDTO responseDto = new NotificationDTO(
                created.getTitle(),
                created.getMessage(),
                created.getCreatedAt()
        );
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getAllNotifications() {
        List<Notification> notifications = notificationService.getAllNotifications();
        List<NotificationDTO> dtoList = notifications.stream()
                .map(n -> new NotificationDTO(n.getTitle(), n.getMessage(), n.getCreatedAt()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }
}