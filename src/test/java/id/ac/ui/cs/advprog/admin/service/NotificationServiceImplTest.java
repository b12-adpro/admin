package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.dto.NotificationDTO;
import id.ac.ui.cs.advprog.admin.dto.UserDTO;
import id.ac.ui.cs.advprog.admin.model.Notification;
import id.ac.ui.cs.advprog.admin.service.observer.NotificationPublisher;
import id.ac.ui.cs.advprog.admin.repository.NotificationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static id.ac.ui.cs.advprog.admin.enums.UserRole.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationServiceImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private NotificationPublisher notificationPublisher;

    private NotificationServiceImpl notificationService;

    @Mock
    private UserService userService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        notificationService = new NotificationServiceImpl(notificationRepository, notificationPublisher, userService);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testCreateNotification() {
        String title = "System Update";
        String content = "We will perform maintenance.";

        Notification mockNotification = new Notification();
        mockNotification.setId(UUID.randomUUID());
        mockNotification.setTitle(title);
        mockNotification.setMessage(content);
        mockNotification.setCreatedAt(LocalDateTime.now());

        // Mock UserService
        when(userService.getAllActiveUsers()).thenReturn(List.of(
                new UserDTO(UUID.randomUUID(), "A", FUNDRAISER, false),
                new UserDTO(UUID.randomUUID(), "B", DONATUR, false)
        ));

        when(notificationRepository.save(any(Notification.class))).thenReturn(mockNotification);

        NotificationDTO result = notificationService.createNotification(title, content);

        assertNotNull(result);
        assertEquals(title, result.getTitle());
        assertEquals(content, result.getMessage());
        assertNotNull(result.getCreatedAt());

        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    void testGetAllNotifications() {
        Notification notification1 = new Notification();
        notification1.setId(UUID.randomUUID());  // Use UUID for ID
        notification1.setTitle("Notif 1");

        Notification notification2 = new Notification();
        notification2.setId(UUID.randomUUID());  // Use UUID for ID
        notification2.setTitle("Notif 2");

        when(notificationRepository.findAll()).thenReturn(Arrays.asList(notification1, notification2));

        List<NotificationDTO> result = notificationService.getAllNotifications();

        assertEquals(2, result.size());
        assertEquals("Notif 1", result.get(0).getTitle());
        assertEquals("Notif 2", result.get(1).getTitle());

        verify(notificationRepository, times(1)).findAll();
    }

    @Test
    void testGetNotificationById() {
        UUID notificationId = UUID.randomUUID();  // Use UUID for ID
        Notification notification = new Notification();
        notification.setId(notificationId);
        notification.setTitle("Test");

        when(notificationRepository.findById(notificationId)).thenReturn(Optional.of(notification));

        NotificationDTO result = notificationService.getNotificationDTOById(notificationId);

        assertNotNull(result);
        assertEquals(notificationId, result.getId());
        assertEquals("Test", result.getTitle());

        verify(notificationRepository, times(1)).findById(notificationId);
    }
}
