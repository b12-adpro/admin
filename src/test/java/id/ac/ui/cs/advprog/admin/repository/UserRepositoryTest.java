package id.ac.ui.cs.advprog.admin.repository;

import id.ac.ui.cs.advprog.admin.enums.UserRole;
import id.ac.ui.cs.advprog.admin.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User createTestUser(String username, String email, UserRole role, boolean isBlocked) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword("test123"); // Required field
        user.setNotifications(new ArrayList<>()); // Required field
        user.setRole(role);
        user.setBlocked(isBlocked);
        return user;
    }

    @Test
    @DisplayName("Should find user by username")
    void testFindByUsername() {
        User user = createTestUser("john_doe", "john@example.com", UserRole.FUNDRAISER, false);
        userRepository.save(user);

        Optional<User> found = userRepository.findByUsername("john_doe");
        assertTrue(found.isPresent());
        assertEquals("john@example.com", found.get().getEmail());
    }

    @Test
    @DisplayName("Should find user by email")
    void testFindByEmail() {
        User user = createTestUser("jane_doe", "jane@example.com", UserRole.DONATUR, false);
        userRepository.save(user);

        Optional<User> found = userRepository.findByEmail("jane@example.com");
        assertTrue(found.isPresent());
        assertEquals("jane_doe", found.get().getUsername());
    }

    @Test
    @DisplayName("Should find users by role")
    void testFindByRole() {
        User user1 = createTestUser("user1", "user1@example.com", UserRole.DONATUR, false);
        User user2 = createTestUser("user2", "user2@example.com", UserRole.DONATUR, false);

        userRepository.saveAll(List.of(user1, user2));

        List<User> donaturs = userRepository.findByRole(UserRole.DONATUR);
        assertEquals(2, donaturs.size());
    }

    @Test
    @DisplayName("Should count users by role")
    void testCountByRole() {
        User user = createTestUser("fundraiser", "fundraiser@example.com", UserRole.FUNDRAISER, false);
        userRepository.save(user);

        long count = userRepository.countByRole(UserRole.FUNDRAISER);
        assertEquals(1, count);
    }

    @Test
    @DisplayName("Should find users by block status")
    void testFindByIsBlocked() {
        User blockedUser = createTestUser("blocked", "blocked@example.com", UserRole.FUNDRAISER, true);
        User activeUser = createTestUser("active", "active@example.com", UserRole.FUNDRAISER, false);

        userRepository.saveAll(List.of(blockedUser, activeUser));

        List<User> blocked = userRepository.findByIsBlocked(true);
        assertEquals(1, blocked.size());
        assertEquals("blocked", blocked.get(0).getUsername());
    }
}
