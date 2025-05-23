package id.ac.ui.cs.advprog.admin.service.observer;

import id.ac.ui.cs.advprog.admin.dto.UserDTO;
import id.ac.ui.cs.advprog.admin.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.UUID;

import static id.ac.ui.cs.advprog.admin.enums.UserRole.*;
import static org.mockito.Mockito.*;

class UserNotificationListenerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserNotificationListener listener;

    @Captor
    ArgumentCaptor<String> messageCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testOnNotification_sendsToOnlyActiveUsers() {
        // Arrange
        UserDTO user1 = new UserDTO(UUID.randomUUID(), "Siti", DONATUR, false); // active
        UserDTO user2 = new UserDTO(UUID.randomUUID(), "Budi", FUNDRAISER, true); // blocked
        UserDTO user3 = new UserDTO(UUID.randomUUID(), "Andi", FUNDRAISER, false); // active

        when(userService.getAllActiveUsers()).thenReturn(List.of(user1, user3));
        listener.onNotification("Test message");

        verify(userService, times(1)).getAllActiveUsers();
    }
}
