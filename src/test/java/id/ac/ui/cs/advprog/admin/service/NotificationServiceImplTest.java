package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.model.Notification;
import id.ac.ui.cs.advprog.admin.model.User;
import id.ac.ui.cs.advprog.admin.repository.NotificationRepository;
import id.ac.ui.cs.advprog.admin.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationServiceImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create and return notification")
    void testCreateNotification() {
        String title = "System Update";
        String content = "We will perform maintenance.";

        User user1 = new User();
        user1.setUsername("user1");

        User user2 = new User();
        user2.setUsername("user2");

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        Notification mockNotification = new Notification();
        mockNotification.setTitle(title);
        mockNotification.setMessage(content);
        mockNotification.setCreatedAt(LocalDateTime.now());

        when(notificationRepository.save(any(Notification.class))).thenReturn(mockNotification);

        Notification result = notificationService.createNotification(title, content);

        assertNotNull(result);
        assertEquals(title, result.getTitle());
        assertEquals(content, result.getMessage());

        verify(notificationRepository, times(1)).save(any(Notification.class));
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return all notifications")
    void testGetAllNotifications() {
        Notification notification1 = new Notification();
        notification1.setTitle("Notif 1");

        Notification notification2 = new Notification();
        notification2.setTitle("Notif 2");

        when(notificationRepository.findAll()).thenReturn(Arrays.asList(notification1, notification2));

        List<Notification> result = notificationService.getAllNotifications();

        assertEquals(2, result.size());
        verify(notificationRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return notification by ID")
    void testGetNotificationById() {
        Notification notification = new Notification();
        notification.setId(1L);
        notification.setTitle("Test");

        when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification));

        Notification result = notificationService.getNotificationById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(notificationRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should throw exception when notification not found")
    void testGetNotificationById_NotFound() {
        when(notificationRepository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> notificationService.getNotificationById(2L));

        assertEquals("Notification not found with id: 2", thrown.getMessage());
    }
}
