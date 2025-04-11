package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.enums.UserRole;
import id.ac.ui.cs.advprog.admin.model.User;
import id.ac.ui.cs.advprog.admin.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return all users")
    void testGetAllUsers() {
        List<User> users = Arrays.asList(new User(), new User());
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return user by id")
    void testGetUserById() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    @DisplayName("Should block user")
    void testBlockUser() {
        User user = new User();
        user.setId(1L);
        user.setBlocked(false);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        User result = userService.blockUser(1L);

        assertTrue(result.isBlocked());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    @DisplayName("Should delete user by id")
    void testDeleteUser() {
        Long id = 1L;
        userService.deleteUser(id);
        verify(userRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Should return true if user is blocked")
    void testIsUserBlockedTrue() {
        User user = new User();
        user.setBlocked(true);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        boolean isBlocked = userService.isUserBlocked(1L);

        assertTrue(isBlocked);
    }

    @Test
    @DisplayName("Should return false if user is not blocked")
    void testIsUserBlockedFalse() {
        User user = new User();
        user.setBlocked(false);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        boolean isBlocked = userService.isUserBlocked(1L);

        assertFalse(isBlocked);
    }

    @Test
    @DisplayName("Should get all active users")
    void testGetAllActiveUsers() {
        List<User> users = Arrays.asList(new User(), new User());
        when(userRepository.findByIsBlocked(false)).thenReturn(users);

        List<User> result = userService.getAllActiveUsers();

        assertEquals(2, result.size());
        verify(userRepository, times(1)).findByIsBlocked(false);
    }

    @Test
    @DisplayName("Should count all users")
    void testCountAllUsers() {
        when(userRepository.count()).thenReturn(5L);
        int count = userService.countAllUsers();

        assertEquals(5, count);
    }

    @Test
    @DisplayName("Should count users by role")
    void testCountUsersByRole() {
        when(userRepository.countByRole(UserRole.DONATUR)).thenReturn(3L);
        int count = userService.countUsersByRole(UserRole.DONATUR);

        assertEquals(3, count);
    }
}
