package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.dto.UserDTO;
import id.ac.ui.cs.advprog.admin.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl();
    }

    @Test
    void testGetAllUsers() {
        List<?> users = userService.getAllUsers();
        assertEquals(3, users.size());
    }

    @Test
    void testGetUserById() throws Exception {
        Optional<?> userOpt = userService.getUserById(1L);
        assertTrue(userOpt.isPresent());

        Object user = userOpt.get();
        Method getName = user.getClass().getMethod("getName");
        String name = (String) getName.invoke(user);

        assertEquals("Andi", name);
    }

    @Test
    void testBlockExistingUser() {
        UserDTO user = userService.blockUser(2L);

        assertNotNull(user);
        assertTrue(user.isBlocked());
    }

    @Test
    void testBlockNonExistentUser() {
        UserDTO user = userService.blockUser(999L);
        assertNull(user);
    }

    @Test
    void testIsUserBlockedNonExistent() {
        assertFalse(userService.isUserBlocked(999L));
    }

    @Test
    void testBlockUser() throws Exception {
        Object user = userService.blockUser(2L);
        Method isBlocked = user.getClass().getMethod("isBlocked");
        boolean blocked = (boolean) isBlocked.invoke(user);

        assertTrue(blocked);
    }

    @Test
    void testDeleteUser() {
        userService.deleteUser(1L);
        assertFalse(userService.getUserById(1L).isPresent());
        assertEquals(2, userService.getAllUsers().size());
    }

    @Test
    void testIsUserBlockedTrue() {
        assertTrue(userService.isUserBlocked(3L));
    }

    @Test
    void testIsUserBlockedFalse() {
        assertFalse(userService.isUserBlocked(2L));
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
