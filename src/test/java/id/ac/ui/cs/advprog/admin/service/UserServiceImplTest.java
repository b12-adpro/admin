package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private UserServiceImpl userService;

    private String dummyApiUrl = "http://dummy-api/users";

    private Map<String, Object>[] dummyUsersData;

    @BeforeEach
    void setUp() {
        Map<String, Object> user1 = new HashMap<>();
        user1.put("id", "123e4567-e89b-12d3-a456-426614174000");
        user1.put("fullName", "Alice");
        user1.put("blocked", false);

        Map<String, Object> user2 = new HashMap<>();
        user2.put("id", "223e4567-e89b-12d3-a456-426614174000");
        user2.put("fullName", "Bob");
        user2.put("blocked", true);

        Map<String, Object> user3 = new HashMap<>();
        user3.put("id", "323e4567-e89b-12d3-a456-426614174000");
        user3.put("fullName", "Charlie");
        user3.put("blocked", false);

        dummyUsersData = new Map[]{user1, user2, user3};

        // Inject dummy API URL secara refleksi (karena @Value private)
        try {
            var field = UserServiceImpl.class.getDeclaredField("apiUrl");
            field.setAccessible(true);
            field.set(userService, dummyApiUrl);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            var field = UserServiceImpl.class.getDeclaredField("jwtToken");
            field.setAccessible(true);
            field.set(userService, "Bearer dummy-token");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetAllUsersReturnsUsers() {
        ResponseEntity<Map[]> dummyResponse = new ResponseEntity<>(dummyUsersData, HttpStatus.OK);
        when(restTemplate.exchange(
                eq(dummyApiUrl),
                eq(org.springframework.http.HttpMethod.GET),
                any(),
                eq(Map[].class)
        )).thenReturn(dummyResponse);

        List<UserDTO> users = userService.getAllUsers();

        assertEquals(3, users.size());
        assertEquals("Alice", users.get(0).getName());
        assertFalse(users.get(0).isBlocked());
        assertEquals("Bob", users.get(1).getName());
        assertTrue(users.get(1).isBlocked());
    }

    @Test
    void testGetUserByIdFound() {
        ResponseEntity<Map[]> dummyResponse = new ResponseEntity<>(dummyUsersData, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), any(), any(), eq(Map[].class)))
                .thenReturn(dummyResponse);

        UUID id = UUID.fromString("223e4567-e89b-12d3-a456-426614174000");
        Optional<UserDTO> user = userService.getUserById(id);

        assertTrue(user.isPresent());
        assertEquals("Bob", user.get().getName());
        assertTrue(user.get().isBlocked());
    }

    @Test
    void testGetUserByIdNotFound() {
        ResponseEntity<Map[]> dummyResponse = new ResponseEntity<>(dummyUsersData, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), any(), any(), eq(Map[].class)))
                .thenReturn(dummyResponse);

        UUID id = UUID.fromString("999e6543-e21b-34d3-b123-826614174999");
        Optional<UserDTO> user = userService.getUserById(id);

        assertTrue(user.isEmpty());
    }

    @Test
    void testIsUserBlockedTrue() {
        ResponseEntity<Map[]> dummyResponse = new ResponseEntity<>(dummyUsersData, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), any(), any(), eq(Map[].class)))
                .thenReturn(dummyResponse);

        UUID id = UUID.fromString("223e4567-e89b-12d3-a456-426614174000"); // Bob yang blocked=true
        boolean blocked = userService.isUserBlocked(id);
        assertTrue(blocked);
    }

    @Test
    void testIsUserBlockedFalse() {
        ResponseEntity<Map[]> dummyResponse = new ResponseEntity<>(dummyUsersData, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), any(), any(), eq(Map[].class)))
                .thenReturn(dummyResponse);

        UUID id = UUID.fromString("123e4567-e89b-12d3-a456-426614174000"); // Alice yang blocked=false
        boolean blocked = userService.isUserBlocked(id);
        assertFalse(blocked);
    }

    @Test
    void testGetAllActiveUsers() {
        ResponseEntity<Map[]> dummyResponse = new ResponseEntity<>(dummyUsersData, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), any(), any(), eq(Map[].class)))
                .thenReturn(dummyResponse);

        List<UserDTO> activeUsers = userService.getAllActiveUsers();
        assertEquals(2, activeUsers.size());
        assertTrue(activeUsers.stream().noneMatch(UserDTO::isBlocked));
    }

    @Test
    void testCountAllUsers() {
        ResponseEntity<Map[]> dummyResponse = new ResponseEntity<>(dummyUsersData, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), any(), any(), eq(Map[].class)))
                .thenReturn(dummyResponse);

        int count = userService.countAllUsers();
        assertEquals(3, count);
    }
}