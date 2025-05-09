package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.dto.UserDTO;
import id.ac.ui.cs.advprog.admin.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl();
    }

    @Test
    void testGetAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        assertEquals(3, users.size());
    }

    @Test
    void testBlockNonExistentUser() {
        UUID nonExistentUserId = UUID.fromString("999e6543-e21b-34d3-b123-826614174999");
        UserDTO user = userService.setBlockedStatus(nonExistentUserId, true);
        assertNull(user);
    }

    @Test
    void testIsUserBlockedNonExistent() {
        UUID nonExistentUserId = UUID.fromString("999e6543-e21b-34d3-b123-826614174999");
        assertFalse(userService.isUserBlocked(nonExistentUserId));
    }

    @Test
    void testIsUserBlockedFalse() {
        UUID userId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        assertFalse(userService.isUserBlocked(userId));
    }

    @Test
    void testGetAllActiveUsers() throws Exception {
        List<UserDTO> activeUsers = userService.getAllActiveUsers();
        assertEquals(2, activeUsers.size());
        assertTrue(activeUsers.stream().noneMatch(UserDTO::isBlocked));
    }

    @Test
    void testCountAllUsers() {
        assertEquals(3, userService.countAllUsers());
    }

    @Test
    void testCountUsersByRole() {
        assertEquals(2, userService.countUsersByRole(UserRole.FUNDRAISER));
        assertEquals(1, userService.countUsersByRole(UserRole.DONATUR));
    }
}
