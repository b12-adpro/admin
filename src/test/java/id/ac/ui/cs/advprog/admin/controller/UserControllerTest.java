package id.ac.ui.cs.advprog.admin.controller;

import id.ac.ui.cs.advprog.admin.dto.UserDTO;
import id.ac.ui.cs.advprog.admin.enums.UserRole;
import id.ac.ui.cs.advprog.admin.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserDTO dummyUser1;
    private UserDTO dummyUser2;

    @BeforeEach
    void setUp() {
        dummyUser1 = UserDTO.builder()
                .id(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"))  // Menggunakan UUID
                .name("Andi")
                .role(UserRole.FUNDRAISER)
                .isBlocked(false)
                .build();

        dummyUser2 = UserDTO.builder()
                .id(UUID.fromString("987e6543-e21b-34d3-b123-826614174001"))  // Menggunakan UUID
                .name("Siti")
                .role(UserRole.DONATUR)
                .isBlocked(false)
                .build();
    }

    @Test
    void testGetAllUsers() {
        when(userService.getAllUsers()).thenReturn(List.of(dummyUser1, dummyUser2));

        ResponseEntity<List<UserDTO>> response = userController.getAllUsers();
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetUserById() {
        when(userService.getUserById(dummyUser1.getId())).thenReturn(Optional.of(dummyUser1));

        ResponseEntity<UserDTO> response = userController.getUserById(dummyUser1.getId());
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Andi", response.getBody().getName());
    }
}
